<template>
  <div>
    <!-- Hero Section -->
    <section class="bg-navy-900 text-white py-16 lg:py-24">
      <div class="container-custom">
        <div class="max-w-4xl mx-auto text-center">
          <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold mb-6">
            Custom Packaging Products
          </h1>
          <p class="text-xl lg:text-2xl text-gray-300 mb-8">
            Discover our comprehensive range of premium packaging solutions designed to protect your products and elevate your brand.
          </p>
          <div class="flex flex-wrap justify-center gap-8 text-sm">
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
              <span>20+ Years Experience</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"/>
              </svg>
              <span>Factory Direct Pricing</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
              </svg>
              <span>Rapid Prototyping</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Filters and Search -->
    <section class="bg-white border-b border-gray-200 sticky top-16 z-40">
      <div class="container-custom py-6">
        <div class="flex flex-col lg:flex-row gap-4 items-center">
          <!-- Search -->
          <div class="relative flex-1 lg:max-w-md">
            <svg class="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Search products..."
              class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-primary-500 focus:border-primary-500"
              @input="debouncedSearch"
            >
          </div>

          <!-- Category Filter -->
          <div class="flex flex-wrap gap-2">
            <button
              :class="[
                'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                selectedCategory === null
                  ? 'bg-primary-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              ]"
              @click="setCategory(null)"
            >
              All Products
            </button>
            <button
              v-for="category in categories"
              :key="category"
              :class="[
                'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                selectedCategory === category
                  ? 'bg-primary-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              ]"
              @click="setCategory(category)"
            >
              {{ category }}
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- Products Grid -->
    <section class="section">
      <div class="container-custom">
        <!-- Loading State -->
        <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
          <div v-for="i in 8" :key="i" class="animate-pulse">
            <div class="bg-gray-200 aspect-4-3 rounded-xl mb-4"></div>
            <div class="bg-gray-200 h-4 rounded mb-2"></div>
            <div class="bg-gray-200 h-3 rounded w-3/4"></div>
          </div>
        </div>

        <!-- No Results -->
        <div v-else-if="products && products.content.length === 0" class="text-center py-16">
          <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-3-5a9 9 0 11-6.364 0M15.364 7A9 9 0 017 7"/>
          </svg>
          <h3 class="text-xl font-bold text-gray-900 mb-2">No Products Found</h3>
          <p class="text-gray-600 mb-6">
            {{ searchQuery ? `No products match "${searchQuery}"` : 'No products found in this category' }}
          </p>
          <Button variant="secondary" @click="clearFilters">
            Clear Filters
          </Button>
        </div>

        <!-- Products Grid -->
        <div v-else-if="products" class="space-y-8">
          <!-- Results Count -->
          <div class="flex items-center justify-between">
            <p class="text-gray-600">
              Showing {{ products.content.length }} of {{ products.totalElements }} products
              <span v-if="searchQuery || selectedCategory">
                {{ searchQuery ? `for "${searchQuery}"` : '' }}
                {{ selectedCategory ? `in ${selectedCategory}` : '' }}
              </span>
            </p>

            <!-- Sort Options -->
            <select
              v-model="sortBy"
              class="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:ring-primary-500 focus:border-primary-500"
              @change="updateProducts"
            >
              <option value="displayOrder">Default Order</option>
              <option value="name">Name A-Z</option>
              <option value="createdAt">Newest First</option>
            </select>
          </div>

          <!-- Products Grid -->
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
            <Card
              v-for="product in products.content"
              :key="product.id"
              hover
              padding="lg"
              class="group cursor-pointer"
              @click="$router.push(`/products/${product.slug}`)"
            >
              <div class="aspect-4-3 bg-gray-100 rounded-xl overflow-hidden mb-4 relative">
                <img
                  v-if="product.imageUrl"
                  :src="product.imageUrl"
                  :alt="product.name"
                  class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                >
                <div v-else class="w-full h-full flex items-center justify-center bg-gradient-primary">
                  <svg class="w-16 h-16 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                  </svg>
                </div>

                <!-- Category Badge -->
                <Badge v-if="product.category" variant="primary" class="absolute top-3 left-3">
                  {{ product.category }}
                </Badge>
              </div>

              <div class="space-y-3">
                <h3 class="text-lg font-semibold text-navy-900 group-hover:text-primary-600 transition-colors line-clamp-2">
                  {{ product.name }}
                </h3>

                <p v-if="product.shortDescription" class="text-sm text-gray-600 line-clamp-3">
                  {{ product.shortDescription }}
                </p>

                <!-- Key Specs -->
                <div class="space-y-1">
                  <div v-if="product.minimumOrderQuantity" class="flex items-center gap-2 text-xs text-gray-500">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18"/>
                    </svg>
                    <span>Min Order: {{ product.minimumOrderQuantity }} pcs</span>
                  </div>
                  <div v-if="product.leadTime" class="flex items-center gap-2 text-xs text-gray-500">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                    </svg>
                    <span>{{ product.leadTime }}</span>
                  </div>
                </div>

                <!-- Industries -->
                <div v-if="product.targetIndustries && product.targetIndustries.length > 0" class="flex flex-wrap gap-1">
                  <Badge
                    v-for="industry in product.targetIndustries.slice(0, 2)"
                    :key="industry"
                    variant="secondary"
                    size="sm"
                  >
                    {{ industry }}
                  </Badge>
                  <span v-if="product.targetIndustries.length > 2" class="text-xs text-gray-500">
                    +{{ product.targetIndustries.length - 2 }} more
                  </span>
                </div>

                <Button variant="secondary" size="sm" full-width class="mt-4">
                  View Details
                </Button>
              </div>
            </Card>
          </div>

          <!-- Pagination -->
          <div v-if="products.totalPages > 1" class="flex justify-center mt-12">
            <nav class="flex items-center space-x-2">
              <button
                :disabled="currentPage === 0"
                :class="[
                  'px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                  currentPage === 0
                    ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                    : 'bg-white border border-gray-300 text-gray-700 hover:bg-gray-50'
                ]"
                @click="goToPage(currentPage - 1)"
              >
                Previous
              </button>

              <span class="px-4 py-2 text-sm text-gray-700">
                Page {{ currentPage + 1 }} of {{ products.totalPages }}
              </span>

              <button
                :disabled="currentPage >= products.totalPages - 1"
                :class="[
                  'px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                  currentPage >= products.totalPages - 1
                    ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                    : 'bg-white border border-gray-300 text-gray-700 hover:bg-gray-50'
                ]"
                @click="goToPage(currentPage + 1)"
              >
                Next
              </button>
            </nav>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="section bg-primary-600 text-white">
      <div class="container-custom text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-6">
          Can't Find What You're Looking For?
        </h2>
        <p class="text-xl mb-8 opacity-90 max-w-2xl mx-auto">
          We specialize in custom packaging solutions. Let our experts help you find the perfect packaging for your specific needs.
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <Button variant="secondary" size="lg" to="/contact-us">
            Get Custom Quote
          </Button>
          <Button variant="ghost" size="lg" href="tel:+8613728607297">
            Call: +86 137 2860 7297
          </Button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
// SEO Meta Tags
useSeoMeta({
  title: 'Custom Packaging Products | MagerPack Manufacturing',
  ogTitle: 'Custom Packaging Products | MagerPack Manufacturing',
  description: 'Browse our comprehensive range of custom packaging solutions including rigid boxes, folding boxes, display packaging, and more. Factory-direct pricing with rapid prototyping.',
  ogDescription: 'Browse our comprehensive range of custom packaging solutions including rigid boxes, folding boxes, display packaging, and more. Factory-direct pricing with rapid prototyping.',
  ogImage: '/images/products-og.jpg',
  twitterCard: 'summary_large_image'
})

const route = useRoute()
const router = useRouter()

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Reactive state
const searchQuery = ref('')
const selectedCategory = ref<string | null>(null)
const currentPage = ref(0)
const sortBy = ref('displayOrder')
const sortDirection = ref('asc')

// Debounced search
let searchTimeout: NodeJS.Timeout
const debouncedSearch = () => {
  clearTimeout(searchTimeout)
  searchTimeout = setTimeout(() => {
    currentPage.value = 0 // Reset to first page on search
    updateProducts()
  }, 300)
}

// Fetch categories
const { data: categories } = await useFetch(`${apiBaseUrl}/api/public/products/categories`)

// Build query parameters
const buildQuery = () => {
  const params = new URLSearchParams()

  if (selectedCategory.value) params.append('category', selectedCategory.value)
  if (searchQuery.value) params.append('search', searchQuery.value)
  params.append('page', currentPage.value.toString())
  params.append('size', '12')
  params.append('sortBy', sortBy.value)
  params.append('sortDirection', sortDirection.value)

  return params.toString()
}

// Fetch products with reactive parameters
const { data: products, pending, refresh } = await useFetch(() => {
  const query = buildQuery()
  return `${apiBaseUrl}/api/public/products?${query}`
})

// Update products when filters change
const updateProducts = async () => {
  await refresh()
}

// Filter methods
const setCategory = (category: string | null) => {
  selectedCategory.value = category
  currentPage.value = 0
  updateProducts()
}

const clearFilters = () => {
  searchQuery.value = ''
  selectedCategory.value = null
  currentPage.value = 0
  updateProducts()
}

const goToPage = (page: number) => {
  currentPage.value = page
  updateProducts()
  // Scroll to top of products section
  document.querySelector('.section')?.scrollIntoView({ behavior: 'smooth' })
}

// Initialize from URL query params
onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search)

  if (urlParams.get('category')) {
    selectedCategory.value = urlParams.get('category')
  }
  if (urlParams.get('search')) {
    searchQuery.value = urlParams.get('search')!
  }
  if (urlParams.get('page')) {
    currentPage.value = parseInt(urlParams.get('page')!)
  }
})

// Update URL when filters change
watch([selectedCategory, searchQuery, currentPage], () => {
  const params = new URLSearchParams()

  if (selectedCategory.value) params.append('category', selectedCategory.value)
  if (searchQuery.value) params.append('search', searchQuery.value)
  if (currentPage.value > 0) params.append('page', currentPage.value.toString())

  const newUrl = params.toString() ? `?${params.toString()}` : window.location.pathname
  window.history.replaceState({}, '', newUrl)
})
</script>