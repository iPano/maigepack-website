<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="bg-white shadow-sm">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center justify-between">
        <h1 class="text-xl font-bold text-navy-900">Product Management</h1>
        <div class="flex items-center gap-3">
          <NuxtLink
            to="/admin/products/new"
            class="bg-primary-600 hover:bg-primary-700 text-white font-medium px-4 py-2 rounded-lg text-sm transition-colors"
          >
            + Add Product
          </NuxtLink>
          <button
            @click="logout"
            class="text-gray-600 hover:text-gray-900 font-medium px-4 py-2 rounded-lg text-sm border border-gray-300 hover:border-gray-400 transition-colors"
          >
            Logout
          </button>
        </div>
      </div>
    </header>

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Filters -->
      <div class="bg-white rounded-xl shadow-sm p-4 mb-6 flex flex-wrap gap-4">
        <div class="flex-1 min-w-[200px]">
          <input
            v-model="searchInput"
            type="text"
            placeholder="Search by name or description..."
            class="w-full px-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
          />
        </div>
        <div class="w-48">
          <select
            v-model="selectedCategory"
            @change="loadProducts"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
          >
            <option value="">All Categories</option>
            <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
          </select>
        </div>
      </div>

      <!-- Error banner -->
      <div v-if="errorMessage" class="bg-red-50 border border-red-200 text-red-700 rounded-lg px-4 py-3 text-sm mb-6">
        {{ errorMessage }}
      </div>

      <!-- Table -->
      <div class="bg-white rounded-xl shadow-sm overflow-hidden">
        <div v-if="loading" class="flex justify-center py-16">
          <svg class="animate-spin h-8 w-8 text-primary-600" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
          </svg>
        </div>

        <table v-else class="w-full text-sm">
          <thead>
            <tr class="border-b border-gray-200 bg-gray-50 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
              <th class="px-6 py-3">Name</th>
              <th class="px-6 py-3">Category</th>
              <th class="px-6 py-3">Status</th>
              <th class="px-6 py-3">Order</th>
              <th class="px-6 py-3">Created</th>
              <th class="px-6 py-3 text-right">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-if="products.length === 0">
              <td colspan="6" class="px-6 py-12 text-center text-gray-400">
                No products found.
              </td>
            </tr>
            <tr v-for="product in products" :key="product.id" class="hover:bg-gray-50">
              <td class="px-6 py-4 font-medium text-navy-900">{{ product.name }}</td>
              <td class="px-6 py-4 text-gray-600">{{ product.category }}</td>
              <td class="px-6 py-4">
                <span
                  :class="product.active
                    ? 'bg-green-100 text-green-800'
                    : 'bg-red-100 text-red-800'"
                  class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
                >
                  {{ product.active ? 'Active' : 'Inactive' }}
                </span>
              </td>
              <td class="px-6 py-4 text-gray-600">{{ product.displayOrder }}</td>
              <td class="px-6 py-4 text-gray-500">{{ formatDate(product.createdAt) }}</td>
              <td class="px-6 py-4 text-right">
                <div class="flex items-center justify-end gap-2">
                  <NuxtLink
                    :to="`/admin/products/${product.id}/edit`"
                    class="text-primary-600 hover:text-primary-800 font-medium"
                  >
                    Edit
                  </NuxtLink>
                  <button
                    @click="deleteProduct(product.id, product.name)"
                    class="text-red-600 hover:text-red-800 font-medium"
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Pagination -->
        <div v-if="!loading && totalPages > 1" class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
          <p class="text-sm text-gray-500">
            Page {{ currentPage + 1 }} of {{ totalPages }} ({{ totalElements }} products)
          </p>
          <div class="flex gap-2">
            <button
              :disabled="currentPage === 0"
              @click="goToPage(currentPage - 1)"
              class="px-3 py-1.5 text-sm border border-gray-300 rounded-lg disabled:opacity-40 hover:bg-gray-50 transition-colors"
            >
              Previous
            </button>
            <button
              :disabled="currentPage >= totalPages - 1"
              @click="goToPage(currentPage + 1)"
              class="px-3 py-1.5 text-sm border border-gray-300 rounded-lg disabled:opacity-40 hover:bg-gray-50 transition-colors"
            >
              Next
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
definePageMeta({ middleware: 'admin-auth', layout: 'admin' })
useSeoMeta({ title: 'Products | Admin' })

interface ProductSummary {
  id: number
  name: string
  slug: string
  category: string
  active: boolean
  displayOrder: number
  createdAt: string
}

const { apiFetch } = useAdminApi()
const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

const products = ref<ProductSummary[]>([])
const categories = ref<string[]>([])
const loading = ref(false)
const errorMessage = ref('')
const searchInput = ref('')
const selectedCategory = ref('')
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)

// Debounce search
let searchTimer: ReturnType<typeof setTimeout> | null = null
watch(searchInput, () => {
  if (searchTimer) clearTimeout(searchTimer)
  searchTimer = setTimeout(() => {
    currentPage.value = 0
    loadProducts()
  }, 300)
})

const loadProducts = async () => {
  loading.value = true
  errorMessage.value = ''
  try {
    const params = new URLSearchParams({
      page: String(currentPage.value),
      size: '20',
      sortBy: 'displayOrder',
      sortDirection: 'asc'
    })
    if (searchInput.value.trim()) params.set('search', searchInput.value.trim())
    if (selectedCategory.value) params.set('category', selectedCategory.value)

    const data = await apiFetch<{
      content: ProductSummary[]
      totalPages: number
      totalElements: number
      number: number
    }>(`/api/admin/products?${params}`)

    products.value = data.content
    totalPages.value = data.totalPages
    totalElements.value = data.totalElements
    currentPage.value = data.number
  } catch {
    errorMessage.value = 'Failed to load products. Please refresh.'
  } finally {
    loading.value = false
  }
}

const loadCategories = async () => {
  try {
    const data = await $fetch<{ categories: string[] }>(`${apiBaseUrl}/api/public/products/categories`)
    categories.value = data?.categories ?? []
  } catch {
    // non-critical
  }
}

const deleteProduct = async (id: number, name: string) => {
  if (!window.confirm(`Delete "${name}"? This action cannot be undone.`)) return

  try {
    await apiFetch(`/api/admin/products/${id}`, { method: 'DELETE' })
    await loadProducts()
  } catch {
    errorMessage.value = `Failed to delete "${name}". Please try again.`
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
  loadProducts()
}

const logout = () => {
  localStorage.removeItem('adminToken')
  navigateTo('/admin/login')
}

const formatDate = (iso: string) => {
  return new Date(iso).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' })
}

onMounted(() => {
  loadProducts()
  loadCategories()
})
</script>
