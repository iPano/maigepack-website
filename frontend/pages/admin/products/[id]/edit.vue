<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="bg-white shadow-sm">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center gap-4">
        <NuxtLink to="/admin/products" class="text-gray-500 hover:text-gray-700">
          ← Back to Products
        </NuxtLink>
        <h1 class="text-xl font-bold text-navy-900">Edit Product</h1>
      </div>
    </header>

    <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Loading state -->
      <div v-if="loadingProduct" class="flex justify-center py-16">
        <svg class="animate-spin h-8 w-8 text-primary-600" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
        </svg>
      </div>

      <template v-else>
        <!-- Error / success banners -->
        <div v-if="errorMessage" class="bg-red-50 border border-red-200 text-red-700 rounded-lg px-4 py-3 text-sm mb-6">
          {{ errorMessage }}
        </div>

        <form @submit.prevent="handleSubmit" class="space-y-6">
          <!-- Basic Info -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-5">
            <h2 class="text-base font-semibold text-navy-900 border-b border-gray-100 pb-3">Basic Information</h2>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Product Name <span class="text-red-500">*</span>
                </label>
                <input v-model="form.name" type="text" class="input-field" placeholder="Rigid Gift Box" />
                <p v-if="validationErrors.name" class="text-red-500 text-xs mt-1">{{ validationErrors.name }}</p>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Slug</label>
                <input v-model="form.slug" type="text" class="input-field" placeholder="rigid-gift-box" />
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Category <span class="text-red-500">*</span>
                </label>
                <input v-model="form.category" type="text" class="input-field" placeholder="Rigid Boxes" />
                <p v-if="validationErrors.category" class="text-red-500 text-xs mt-1">{{ validationErrors.category }}</p>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Short Description</label>
                <input v-model="form.shortDescription" type="text" class="input-field" placeholder="One-line summary" />
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">
                Full Description <span class="text-red-500">*</span>
              </label>
              <textarea v-model="form.description" rows="5" class="input-field" placeholder="Detailed product description..."></textarea>
              <p v-if="validationErrors.description" class="text-red-500 text-xs mt-1">{{ validationErrors.description }}</p>
            </div>
          </div>

          <!-- Image -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-4">
            <h2 class="text-base font-semibold text-navy-900 border-b border-gray-100 pb-3">Product Image</h2>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Main Image</label>
              <div class="flex items-start gap-4">
                <div class="flex-1">
                  <input
                    type="file"
                    accept="image/jpeg,image/png,image/webp,image/gif"
                    @change="handleImageUpload"
                    class="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-lg file:border-0 file:text-sm file:font-medium file:bg-primary-50 file:text-primary-700 hover:file:bg-primary-100"
                  />
                  <p v-if="uploadError" class="text-red-500 text-xs mt-1">{{ uploadError }}</p>
                  <p v-if="uploadLoading" class="text-gray-500 text-xs mt-1">Uploading...</p>
                </div>
                <img v-if="form.imageUrl" :src="form.imageUrl" alt="Preview" class="w-24 h-24 object-cover rounded-lg border border-gray-200" />
              </div>
              <div class="mt-2">
                <label class="block text-xs text-gray-500 mb-1">Or enter image URL directly:</label>
                <input v-model="form.imageUrl" type="url" class="input-field" placeholder="https://cdn.example.com/image.jpg" />
              </div>
            </div>

            <!-- Additional Images -->
            <div>
              <div class="flex items-center justify-between mb-2">
                <label class="block text-sm font-medium text-gray-700">Additional Images</label>
                <button type="button" @click="addAdditionalImage" class="text-primary-600 hover:text-primary-800 text-sm font-medium">+ Add</button>
              </div>
              <div v-for="(_, i) in form.additionalImages" :key="i" class="flex gap-2 mb-2">
                <input v-model="form.additionalImages[i]" type="url" class="input-field flex-1" placeholder="https://cdn.example.com/image.jpg" />
                <button type="button" @click="removeAdditionalImage(i)" class="text-red-500 hover:text-red-700 px-2">✕</button>
              </div>
            </div>
          </div>

          <!-- Product Details -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-5">
            <h2 class="text-base font-semibold text-navy-900 border-b border-gray-100 pb-3">Product Details</h2>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Material Options</label>
                <input v-model="form.materialOptions" type="text" class="input-field" placeholder="Grey board, Chipboard" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Finish Options</label>
                <input v-model="form.finishOptions" type="text" class="input-field" placeholder="Matte lamination, Gloss UV" />
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Size Range</label>
                <input v-model="form.sizeRange" type="text" class="input-field" placeholder="100×80×50mm to 400×300×200mm" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Lead Time</label>
                <input v-model="form.leadTime" type="text" class="input-field" placeholder="15–20 days" />
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Minimum Order Quantity</label>
                <input v-model.number="form.minimumOrderQuantity" type="number" min="1" class="input-field" placeholder="100" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Display Order</label>
                <input v-model.number="form.displayOrder" type="number" min="0" class="input-field" />
              </div>
            </div>

            <div class="flex items-center gap-3">
              <label class="relative inline-flex items-center cursor-pointer">
                <input type="checkbox" v-model="form.active" class="sr-only peer" />
                <div class="w-10 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-0.5 after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-600"></div>
              </label>
              <span class="text-sm font-medium text-gray-700">Active (visible on public site)</span>
            </div>
          </div>

          <!-- Features -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-3">
            <div class="flex items-center justify-between border-b border-gray-100 pb-3">
              <h2 class="text-base font-semibold text-navy-900">Features</h2>
              <button type="button" @click="addFeature" class="text-primary-600 hover:text-primary-800 text-sm font-medium">+ Add Feature</button>
            </div>
            <div v-for="(_, i) in form.features" :key="i" class="flex gap-2">
              <input v-model="form.features[i]" type="text" class="input-field flex-1" placeholder="Magnetic closure" />
              <button type="button" @click="removeFeature(i)" class="text-red-500 hover:text-red-700 px-2">✕</button>
            </div>
            <p v-if="form.features.length === 0" class="text-sm text-gray-400">No features added.</p>
          </div>

          <!-- Specifications -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-3">
            <div class="flex items-center justify-between border-b border-gray-100 pb-3">
              <h2 class="text-base font-semibold text-navy-900">Specifications</h2>
              <button type="button" @click="addSpec" class="text-primary-600 hover:text-primary-800 text-sm font-medium">+ Add Spec</button>
            </div>
            <div v-for="(spec, i) in specEntries" :key="i" class="flex gap-2">
              <input v-model="spec.key" type="text" class="input-field w-40" placeholder="Material" />
              <input v-model="spec.value" type="text" class="input-field flex-1" placeholder="Grey board" />
              <button type="button" @click="removeSpec(i)" class="text-red-500 hover:text-red-700 px-2">✕</button>
            </div>
            <p v-if="specEntries.length === 0" class="text-sm text-gray-400">No specifications added.</p>
          </div>

          <!-- Target Industries -->
          <div class="bg-white rounded-xl shadow-sm p-6 space-y-3">
            <div class="flex items-center justify-between border-b border-gray-100 pb-3">
              <h2 class="text-base font-semibold text-navy-900">Target Industries</h2>
              <button type="button" @click="addIndustry" class="text-primary-600 hover:text-primary-800 text-sm font-medium">+ Add Industry</button>
            </div>
            <div v-for="(_, i) in form.targetIndustries" :key="i" class="flex gap-2">
              <input v-model="form.targetIndustries[i]" type="text" class="input-field flex-1" placeholder="Electronics" />
              <button type="button" @click="removeIndustry(i)" class="text-red-500 hover:text-red-700 px-2">✕</button>
            </div>
            <p v-if="form.targetIndustries.length === 0" class="text-sm text-gray-400">No industries added.</p>
          </div>

          <!-- Submit -->
          <div class="flex items-center justify-end gap-4 pb-8">
            <NuxtLink to="/admin/products" class="px-6 py-2.5 text-sm font-medium text-gray-700 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors">
              Cancel
            </NuxtLink>
            <button
              type="submit"
              :disabled="saving"
              class="px-6 py-2.5 text-sm font-semibold bg-primary-600 hover:bg-primary-700 disabled:opacity-50 text-white rounded-lg transition-colors"
            >
              <span v-if="saving">Saving...</span>
              <span v-else>Save Changes</span>
            </button>
          </div>
        </form>
      </template>
    </main>
  </div>
</template>

<script setup lang="ts">
definePageMeta({ middleware: 'admin-auth', layout: 'admin' })
useSeoMeta({ title: 'Edit Product | Admin' })

const route = useRoute()
const productId = route.params.id as string
const { apiFetch } = useAdminApi()
const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

const loadingProduct = ref(true)
const saving = ref(false)
const uploadLoading = ref(false)
const uploadError = ref('')
const errorMessage = ref('')
const validationErrors = reactive<Record<string, string>>({})

const form = reactive({
  name: '',
  slug: '',
  shortDescription: '',
  description: '',
  category: '',
  imageUrl: '',
  additionalImages: [] as string[],
  features: [] as string[],
  targetIndustries: [] as string[],
  materialOptions: '',
  finishOptions: '',
  sizeRange: '',
  minimumOrderQuantity: null as number | null,
  leadTime: '',
  active: true,
  displayOrder: 0
})

const specEntries = ref<{ key: string; value: string }[]>([])

// List helpers
const addAdditionalImage = () => form.additionalImages.push('')
const removeAdditionalImage = (i: number) => form.additionalImages.splice(i, 1)
const addFeature = () => form.features.push('')
const removeFeature = (i: number) => form.features.splice(i, 1)
const addIndustry = () => form.targetIndustries.push('')
const removeIndustry = (i: number) => form.targetIndustries.splice(i, 1)
const addSpec = () => specEntries.value.push({ key: '', value: '' })
const removeSpec = (i: number) => specEntries.value.splice(i, 1)

const ALLOWED_IMAGE_TYPES = ['image/jpeg', 'image/png', 'image/webp', 'image/gif']

const handleImageUpload = async (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (!file) return

  if (!ALLOWED_IMAGE_TYPES.includes(file.type)) {
    uploadError.value = 'Invalid file type. Allowed: JPEG, PNG, WebP, GIF.'
    return
  }
  uploadError.value = ''
  uploadLoading.value = true

  try {
    const token = localStorage.getItem('adminToken')
    const formData = new FormData()
    formData.append('file', file)

    const data = await $fetch<{ url: string }>(`${apiBaseUrl}/api/admin/uploads`, {
      method: 'POST',
      headers: token ? { Authorization: `Bearer ${token}` } : {},
      body: formData
    })
    form.imageUrl = data.url
  } catch {
    uploadError.value = 'Upload failed. Please try again or enter the URL manually.'
  } finally {
    uploadLoading.value = false
  }
}

const validate = (): boolean => {
  Object.keys(validationErrors).forEach(k => delete validationErrors[k])
  if (!form.name.trim()) validationErrors.name = 'Product name is required.'
  if (!form.category.trim()) validationErrors.category = 'Category is required.'
  if (!form.description.trim()) validationErrors.description = 'Description is required.'
  return Object.keys(validationErrors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return

  saving.value = true
  errorMessage.value = ''

  const specifications: Record<string, string> = {}
  for (const { key, value } of specEntries.value) {
    if (key.trim()) specifications[key.trim()] = value.trim()
  }

  const payload = {
    ...form,
    additionalImages: form.additionalImages.filter(s => s.trim()),
    features: form.features.filter(s => s.trim()),
    targetIndustries: form.targetIndustries.filter(s => s.trim()),
    specifications,
    slug: form.slug.trim() || null
  }

  try {
    await apiFetch(`/api/admin/products/${productId}`, { method: 'PUT', body: payload })
    await navigateTo('/admin/products')
  } catch {
    errorMessage.value = 'Failed to save changes. Please check your inputs and try again.'
  } finally {
    saving.value = false
  }
}

onMounted(async () => {
  try {
    const product = await apiFetch<{
      id: number
      name: string
      slug: string
      shortDescription: string | null
      description: string
      category: string
      imageUrl: string | null
      additionalImages: string[] | null
      features: string[] | null
      specifications: Record<string, string> | null
      targetIndustries: string[] | null
      materialOptions: string | null
      finishOptions: string | null
      sizeRange: string | null
      minimumOrderQuantity: number | null
      leadTime: string | null
      active: boolean
      displayOrder: number
    }>(`/api/admin/products/${productId}`)

    form.name = product.name
    form.slug = product.slug
    form.shortDescription = product.shortDescription ?? ''
    form.description = product.description
    form.category = product.category
    form.imageUrl = product.imageUrl ?? ''
    form.additionalImages = product.additionalImages ? [...product.additionalImages] : []
    form.features = product.features ? [...product.features] : []
    form.targetIndustries = product.targetIndustries ? [...product.targetIndustries] : []
    form.materialOptions = product.materialOptions ?? ''
    form.finishOptions = product.finishOptions ?? ''
    form.sizeRange = product.sizeRange ?? ''
    form.minimumOrderQuantity = product.minimumOrderQuantity ?? null
    form.leadTime = product.leadTime ?? ''
    form.active = product.active
    form.displayOrder = product.displayOrder

    if (product.specifications) {
      specEntries.value = Object.entries(product.specifications).map(([key, value]) => ({ key, value }))
    }
  } catch {
    errorMessage.value = 'Failed to load product. It may not exist.'
  } finally {
    loadingProduct.value = false
  }
})
</script>

<style scoped>
.input-field {
  @apply w-full px-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent;
}
</style>
