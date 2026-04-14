<template>
  <div>
    <!-- Hero -->
    <section class="bg-navy-900 text-white py-16 lg:py-20">
      <div class="container-custom">
        <nav class="flex items-center space-x-2 text-sm mb-6 text-gray-400">
          <NuxtLink to="/" class="hover:text-white transition-colors">Home</NuxtLink>
          <span>/</span>
          <NuxtLink to="/products" class="hover:text-white transition-colors">Products</NuxtLink>
          <span>/</span>
          <span class="text-white">{{ categoryName }}</span>
        </nav>
        <h1 class="text-4xl md:text-5xl font-bold mb-4">{{ categoryName }}</h1>
        <p class="text-xl text-gray-300 max-w-2xl">
          Browse our range of custom {{ categoryName.toLowerCase() }} — factory-direct pricing,
          rapid sampling, and full customisation.
        </p>
      </div>
    </section>

    <!-- Products Grid -->
    <section class="section">
      <div class="container-custom">
        <!-- Loading -->
        <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
          <div v-for="i in 8" :key="i" class="animate-pulse">
            <div class="bg-gray-200 aspect-4-3 rounded-xl mb-4"></div>
            <div class="bg-gray-200 h-4 rounded mb-2"></div>
            <div class="bg-gray-200 h-3 rounded w-3/4"></div>
          </div>
        </div>

        <!-- Empty -->
        <div v-else-if="!products || products.length === 0" class="text-center py-20">
          <svg class="w-16 h-16 text-gray-300 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
          </svg>
          <h2 class="text-xl font-semibold text-gray-700 mb-2">No products yet</h2>
          <p class="text-gray-500 mb-6">Check back soon or browse all products.</p>
          <Button variant="primary" to="/products">View All Products</Button>
        </div>

        <!-- Grid -->
        <div v-else>
          <p class="text-gray-500 mb-8">{{ products.length }} product{{ products.length !== 1 ? 's' : '' }} found</p>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
            <Card
              v-for="product in products"
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
              </div>

              <div class="space-y-3">
                <h3 class="text-lg font-semibold text-navy-900 group-hover:text-primary-600 transition-colors line-clamp-2">
                  {{ product.name }}
                </h3>
                <p v-if="product.shortDescription" class="text-sm text-gray-600 line-clamp-3">
                  {{ product.shortDescription }}
                </p>
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
                <Button variant="secondary" size="sm" full-width class="mt-4">
                  View Details
                </Button>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA -->
    <section class="section bg-primary-600 text-white">
      <div class="container-custom text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-4">Need Custom {{ categoryName }}?</h2>
        <p class="text-xl mb-8 opacity-90 max-w-2xl mx-auto">
          Tell us your requirements and we'll respond with a detailed quote within 24 hours.
        </p>
        <Button variant="secondary" size="lg" to="/contact-us">Get Free Quote</Button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
const route = useRoute()
const slug = route.params.slug as string

// Convert slug → display name: "rigid-boxes" → "Rigid Boxes"
const categoryName = slug
  .split('-')
  .map((w: string) => w.charAt(0).toUpperCase() + w.slice(1))
  .join(' ')

// Convert slug → backend category name (same as display name for now)
const categoryParam = categoryName

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// SSR-safe fetch — graceful empty on backend failure
const { data: products, pending } = await useAsyncData(
  `category-${slug}`,
  () => $fetch<any[]>(`${apiBaseUrl}/api/public/products/category/${encodeURIComponent(categoryParam)}`).catch(() => []),
  { default: () => [] as any[] }
)

// Per-category SEO meta
useSeoMeta({
  title: `${categoryName} | Custom Packaging | MagerPack`,
  ogTitle: `${categoryName} | Custom Packaging | MagerPack`,
  description: `Browse MagerPack's custom ${categoryName.toLowerCase()} — factory-direct pricing, full customisation, rapid sampling. Trusted by global brands.`,
  ogDescription: `Browse MagerPack's custom ${categoryName.toLowerCase()} — factory-direct pricing, full customisation, rapid sampling. Trusted by global brands.`,
  twitterCard: 'summary_large_image'
})

// JSON-LD BreadcrumbList for Google
useHead({
  script: [{
    type: 'application/ld+json',
    children: JSON.stringify({
      '@context': 'https://schema.org',
      '@type': 'BreadcrumbList',
      itemListElement: [
        { '@type': 'ListItem', position: 1, name: 'Home', item: 'https://magerpack-website.vercel.app/' },
        { '@type': 'ListItem', position: 2, name: 'Products', item: 'https://magerpack-website.vercel.app/products' },
        { '@type': 'ListItem', position: 3, name: categoryName, item: `https://magerpack-website.vercel.app/products/category/${slug}` }
      ]
    })
  }]
})
</script>
