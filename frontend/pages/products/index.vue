<template>
  <div>
    <!-- Page Header -->
    <section class="bg-navy-900 text-white py-12">
      <div class="container-custom">
        <!-- Breadcrumb -->
        <nav class="flex items-center space-x-2 text-sm text-gray-400 mb-4">
          <NuxtLink to="/" class="hover:text-white transition-colors">Home</NuxtLink>
          <span>/</span>
          <span class="text-white">Products</span>
        </nav>
        <h1 class="text-3xl md:text-4xl font-bold mb-3">Custom Packaging Products</h1>
        <p class="text-gray-300 max-w-2xl">
          Premium packaging solutions manufactured to your specifications. Factory-direct pricing with rapid prototyping.
        </p>
      </div>
    </section>

    <!-- Category Tabs + Search (sticky) -->
    <div class="bg-white border-b border-gray-200 sticky top-16 z-40">
      <div class="container-custom">
        <!-- Category Tabs -->
        <div class="flex items-center gap-0 overflow-x-auto scrollbar-hide border-b border-gray-100">
          <button
            :class="[
              'whitespace-nowrap px-5 py-4 text-sm font-medium border-b-2 transition-colors flex-shrink-0',
              selectedCategory === null
                ? 'border-primary-600 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-800 hover:border-gray-300'
            ]"
            @click="setCategory(null)"
          >
            All Products
          </button>
          <button
            v-for="category in categories"
            :key="category"
            :class="[
              'whitespace-nowrap px-5 py-4 text-sm font-medium border-b-2 transition-colors flex-shrink-0',
              selectedCategory === category
                ? 'border-primary-600 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-800 hover:border-gray-300'
            ]"
            @click="setCategory(category)"
          >
            {{ category }}
          </button>
        </div>

        <!-- Search row -->
        <div class="flex items-center gap-4 py-3">
          <div class="relative flex-1 max-w-sm">
            <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Search products..."
              class="w-full pl-9 pr-4 py-2 text-sm border border-gray-200 rounded-lg focus:ring-primary-500 focus:border-primary-500"
              @input="debouncedSearch"
            >
          </div>
          <p v-if="products" class="text-sm text-gray-400 flex-shrink-0">
            {{ products.totalElements }} product{{ products.totalElements !== 1 ? 's' : '' }}
          </p>
        </div>
      </div>
    </div>

    <!-- Products Grid -->
    <section class="py-12 bg-gray-50 min-h-screen">
      <div class="container-custom">

        <!-- Loading Skeleton -->
        <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="i in 9" :key="i" class="bg-white rounded-xl overflow-hidden border border-gray-100 animate-pulse">
            <div class="bg-gray-200" style="height: 220px;"></div>
            <div class="p-5 space-y-3">
              <div class="h-4 bg-gray-200 rounded w-3/4"></div>
              <div class="h-3 bg-gray-200 rounded"></div>
              <div class="h-3 bg-gray-200 rounded w-5/6"></div>
              <div class="h-8 bg-gray-200 rounded mt-4"></div>
            </div>
          </div>
        </div>

        <!-- No Results -->
        <div v-else-if="products && products.content.length === 0" class="text-center py-24">
          <svg class="w-14 h-14 text-gray-300 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-3-5a9 9 0 11-6.364 0M15.364 7A9 9 0 017 7"/>
          </svg>
          <h3 class="text-lg font-semibold text-gray-700 mb-2">No products found</h3>
          <p class="text-gray-500 mb-6 text-sm">
            {{ searchQuery ? `No results for "${searchQuery}"` : 'No products in this category yet.' }}
          </p>
          <button
            class="text-sm text-primary-600 underline"
            @click="clearFilters"
          >
            Clear filters
          </button>
        </div>

        <!-- Grid -->
        <div v-else-if="products" class="space-y-10">
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <ProductCard
              v-for="product in products.content"
              :key="product.id"
              :product="product"
            />
          </div>

          <!-- Pagination -->
          <div v-if="products.totalPages > 1" class="flex justify-center">
            <nav class="inline-flex items-center gap-1 bg-white border border-gray-200 rounded-xl px-2 py-1.5 shadow-sm">
              <button
                :disabled="currentPage === 0"
                :class="[
                  'px-3 py-1.5 rounded-lg text-sm font-medium transition-colors',
                  currentPage === 0
                    ? 'text-gray-300 cursor-not-allowed'
                    : 'text-gray-600 hover:bg-gray-100'
                ]"
                @click="goToPage(currentPage - 1)"
              >
                ← Prev
              </button>

              <span class="px-4 py-1.5 text-sm text-gray-700 font-medium">
                {{ currentPage + 1 }} / {{ products.totalPages }}
              </span>

              <button
                :disabled="currentPage >= products.totalPages - 1"
                :class="[
                  'px-3 py-1.5 rounded-lg text-sm font-medium transition-colors',
                  currentPage >= products.totalPages - 1
                    ? 'text-gray-300 cursor-not-allowed'
                    : 'text-gray-600 hover:bg-gray-100'
                ]"
                @click="goToPage(currentPage + 1)"
              >
                Next →
              </button>
            </nav>
          </div>
        </div>

      </div>
    </section>

    <!-- CTA Banner -->
    <section class="bg-navy-900 text-white py-16">
      <div class="container-custom text-center">
        <h2 class="text-2xl md:text-3xl font-bold mb-4">
          Can't Find What You're Looking For?
        </h2>
        <p class="text-gray-300 mb-8 max-w-xl mx-auto">
          We specialize in custom packaging. Let our experts help you find the perfect solution for your needs.
        </p>
        <div class="flex flex-col sm:flex-row gap-3 justify-center">
          <NuxtLink
            to="/contact-us"
            class="bg-orange-500 hover:bg-orange-600 text-white font-semibold px-8 py-3 rounded-lg transition-colors"
          >
            Get Custom Quote
          </NuxtLink>
          <a
            href="tel:+8613728607297"
            class="border border-white text-white hover:bg-white hover:text-navy-900 font-semibold px-8 py-3 rounded-lg transition-colors"
          >
            Call: +86 137 2860 7297
          </a>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
// SEO Meta Tags
useSeo({
  title: 'Custom Packaging Products | MagerPack Manufacturing',
  description: 'Browse our comprehensive range of custom packaging solutions including rigid boxes, folding boxes, display packaging, and more. Factory-direct pricing with rapid prototyping.',
  ogImage: '/images/products-og.jpg',
  path: '/products'
})

const route = useRoute()
const router = useRouter()

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Reactive state
const searchQuery = ref('')
const selectedCategory = ref<string | null>(null)
const currentPage = ref(0)

// Debounced search
let searchTimeout: NodeJS.Timeout
const debouncedSearch = () => {
  clearTimeout(searchTimeout)
  searchTimeout = setTimeout(() => {
    currentPage.value = 0
    updateProducts()
  }, 300)
}

// Fetch categories
const { data: categories } = await useFetch(`${apiBaseUrl}/api/public/products/categories`, { server: false })

// Build query parameters
const buildQuery = () => {
  const params = new URLSearchParams()
  if (selectedCategory.value) params.append('category', selectedCategory.value)
  if (searchQuery.value) params.append('search', searchQuery.value)
  params.append('page', currentPage.value.toString())
  params.append('size', '12')
  params.append('sortBy', 'displayOrder')
  params.append('sortDirection', 'asc')
  return params.toString()
}

// Fetch products
const { data: products, pending, refresh } = await useFetch(() => {
  return `${apiBaseUrl}/api/public/products?${buildQuery()}`
}, { server: false })

const updateProducts = async () => {
  await refresh()
}

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
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

// Initialize from URL query params
onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search)
  if (urlParams.get('category')) selectedCategory.value = urlParams.get('category')
  if (urlParams.get('search')) searchQuery.value = urlParams.get('search')!
  if (urlParams.get('page')) currentPage.value = parseInt(urlParams.get('page')!)
})

// Sync filters → URL
watch([selectedCategory, searchQuery, currentPage], () => {
  const params = new URLSearchParams()
  if (selectedCategory.value) params.append('category', selectedCategory.value)
  if (searchQuery.value) params.append('search', searchQuery.value)
  if (currentPage.value > 0) params.append('page', currentPage.value.toString())
  const newUrl = params.toString() ? `?${params.toString()}` : window.location.pathname
  window.history.replaceState({}, '', newUrl)
})
</script>
