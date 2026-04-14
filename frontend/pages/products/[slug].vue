<template>
  <div>
    <!-- Loading State -->
    <div v-if="pending" class="min-h-screen flex items-center justify-center">
      <div class="text-center">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto mb-4"></div>
        <p class="text-gray-600">Loading product details...</p>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error || !product" class="min-h-screen flex items-center justify-center">
      <div class="text-center">
        <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-3-5a9 9 0 11-6.364 0M15.364 7A9 9 0 017 7"/>
        </svg>
        <h1 class="text-2xl font-bold text-gray-900 mb-2">Product Not Found</h1>
        <p class="text-gray-600 mb-6">Sorry, we couldn't find the product you're looking for.</p>
        <Button to="/products" variant="primary">
          View All Products
        </Button>
      </div>
    </div>

    <!-- Product Content -->
    <div v-else>
      <!-- Breadcrumb -->
      <section class="bg-gray-50 py-4">
        <div class="container-custom">
          <nav class="flex items-center space-x-2 text-sm">
            <NuxtLink to="/" class="text-gray-500 hover:text-primary-600">Home</NuxtLink>
            <span class="text-gray-400">/</span>
            <NuxtLink to="/products" class="text-gray-500 hover:text-primary-600">Products</NuxtLink>
            <span class="text-gray-400">/</span>
            <span class="text-gray-900 font-medium">{{ product.name }}</span>
          </nav>
        </div>
      </section>

      <!-- Product Hero -->
      <section class="section">
        <div class="container-custom">
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 items-start">
            <!-- Product Images -->
            <div class="space-y-4">
              <div class="aspect-4-3 bg-gray-100 rounded-xl overflow-hidden">
                <img
                  v-if="product.imageUrl"
                  :src="product.imageUrl"
                  :alt="product.name"
                  class="w-full h-full object-cover"
                >
                <div v-else class="w-full h-full flex items-center justify-center bg-gradient-primary">
                  <svg class="w-24 h-24 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                  </svg>
                </div>
              </div>

              <!-- Additional Images -->
              <div v-if="product.additionalImages && product.additionalImages.length > 0" class="grid grid-cols-4 gap-2">
                <div
                  v-for="(image, index) in product.additionalImages.slice(0, 4)"
                  :key="index"
                  class="aspect-square bg-gray-100 rounded-lg overflow-hidden"
                >
                  <img :src="image" :alt="`${product.name} ${index + 1}`" class="w-full h-full object-cover">
                </div>
              </div>
            </div>

            <!-- Product Info -->
            <div class="space-y-8">
              <div>
                <Badge v-if="product.category" variant="secondary" class="mb-4">
                  {{ product.category }}
                </Badge>
                <h1 class="text-3xl md:text-4xl font-bold text-navy-900 mb-4">
                  {{ product.name }}
                </h1>
                <p v-if="product.shortDescription" class="text-xl text-gray-600 leading-relaxed">
                  {{ product.shortDescription }}
                </p>
              </div>

              <!-- Key Specifications -->
              <div v-if="product.materialOptions || product.sizeRange || product.minimumOrderQuantity" class="bg-gray-50 rounded-xl p-6">
                <h3 class="text-lg font-semibold text-navy-900 mb-4">Quick Specifications</h3>
                <dl class="space-y-3">
                  <div v-if="product.materialOptions" class="flex justify-between">
                    <dt class="text-gray-600">Materials:</dt>
                    <dd class="text-gray-900 font-medium">{{ product.materialOptions }}</dd>
                  </div>
                  <div v-if="product.sizeRange" class="flex justify-between">
                    <dt class="text-gray-600">Size Range:</dt>
                    <dd class="text-gray-900 font-medium">{{ product.sizeRange }}</dd>
                  </div>
                  <div v-if="product.minimumOrderQuantity" class="flex justify-between">
                    <dt class="text-gray-600">Min Order:</dt>
                    <dd class="text-gray-900 font-medium">{{ product.minimumOrderQuantity }} pieces</dd>
                  </div>
                  <div v-if="product.leadTime" class="flex justify-between">
                    <dt class="text-gray-600">Lead Time:</dt>
                    <dd class="text-gray-900 font-medium">{{ product.leadTime }}</dd>
                  </div>
                </dl>
              </div>

              <!-- CTA Section -->
              <div class="flex flex-col sm:flex-row gap-4">
                <Button variant="primary" size="lg" to="/contact-us">
                  Get Free Quote
                </Button>
                <Button variant="secondary" size="lg" href="tel:+8613728607297">
                  Call Now
                </Button>
              </div>

              <!-- Trust Indicators -->
              <div class="flex items-center gap-6 text-sm text-gray-600">
                <div class="flex items-center gap-2">
                  <svg class="w-5 h-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Free Samples</span>
                </div>
                <div class="flex items-center gap-2">
                  <svg class="w-5 h-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>24hr Response</span>
                </div>
                <div class="flex items-center gap-2">
                  <svg class="w-5 h-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <span>Quality Guaranteed</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Product Details -->
      <section class="section bg-white">
        <div class="container-custom">
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
            <!-- Description -->
            <div class="lg:col-span-2 space-y-8">
              <div>
                <h2 class="text-2xl font-bold text-navy-900 mb-6">Product Description</h2>
                <div class="prose prose-gray max-w-none">
                  <p v-if="product.description" v-html="product.description.replace(/\n/g, '<br>')"></p>
                  <p v-else class="text-gray-600">Detailed product description coming soon.</p>
                </div>
              </div>

              <!-- Features -->
              <div v-if="product.features && product.features.length > 0">
                <h3 class="text-xl font-semibold text-navy-900 mb-4">Key Features</h3>
                <ul class="space-y-3">
                  <li v-for="feature in product.features" :key="feature" class="flex items-start gap-3">
                    <svg class="w-5 h-5 text-primary-600 mt-1 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                    </svg>
                    <span class="text-gray-700">{{ feature }}</span>
                  </li>
                </ul>
              </div>

              <!-- Specifications -->
              <div v-if="product.specifications && Object.keys(product.specifications).length > 0">
                <h3 class="text-xl font-semibold text-navy-900 mb-4">Technical Specifications</h3>
                <div class="bg-gray-50 rounded-xl p-6">
                  <dl class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div v-for="(value, key) in product.specifications" :key="key" class="flex flex-col">
                      <dt class="text-sm font-medium text-gray-600 mb-1">{{ key }}</dt>
                      <dd class="text-gray-900">{{ value }}</dd>
                    </div>
                  </dl>
                </div>
              </div>
            </div>

            <!-- Sidebar -->
            <div class="space-y-8">
              <!-- Target Industries -->
              <Card v-if="product.targetIndustries && product.targetIndustries.length > 0" padding="lg">
                <h3 class="text-lg font-semibold text-navy-900 mb-4">Perfect For</h3>
                <div class="space-y-2">
                  <Badge
                    v-for="industry in product.targetIndustries"
                    :key="industry"
                    variant="outline"
                    class="mr-2 mb-2"
                  >
                    {{ industry }}
                  </Badge>
                </div>
              </Card>

              <!-- Quick Quote Card -->
              <Card padding="lg" class="bg-primary-50 border-primary-200">
                <h3 class="text-lg font-semibold text-navy-900 mb-4">Get Your Quote</h3>
                <p class="text-gray-600 mb-6">Ready to order? Get a personalized quote with factory-direct pricing.</p>
                <Button variant="primary" full-width to="/contact-us">
                  Request Quote
                </Button>
                <p class="text-xs text-gray-500 mt-3 text-center">Usually responds in 2-4 hours</p>
              </Card>

              <!-- Contact Info -->
              <Card padding="lg">
                <h3 class="text-lg font-semibold text-navy-900 mb-4">Need Help?</h3>
                <div class="space-y-4">
                  <div class="flex items-center gap-3">
                    <svg class="w-5 h-5 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                    </svg>
                    <div>
                      <p class="text-sm font-medium text-gray-900">Email</p>
                      <a href="mailto:sales@magerpack.com" class="text-sm text-primary-600 hover:underline">
                        sales@magerpack.com
                      </a>
                    </div>
                  </div>
                  <div class="flex items-center gap-3">
                    <svg class="w-5 h-5 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                    </svg>
                    <div>
                      <p class="text-sm font-medium text-gray-900">Phone / WhatsApp</p>
                      <a href="tel:+8613728607297" class="text-sm text-primary-600 hover:underline">
                        +86 137 2860 7297
                      </a>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        </div>
      </section>

      <!-- Related Products -->
      <section v-if="relatedProducts && relatedProducts.length > 0" class="section bg-gray-50">
        <div class="container-custom">
          <h2 class="text-3xl font-bold text-navy-900 mb-12 text-center">Related Products</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <Card
              v-for="relatedProduct in relatedProducts"
              :key="relatedProduct.id"
              hover
              padding="lg"
              class="group cursor-pointer"
              @click="$router.push(`/products/${relatedProduct.slug}`)"
            >
              <div class="aspect-4-3 bg-gray-100 rounded-lg overflow-hidden mb-4">
                <img
                  v-if="relatedProduct.imageUrl"
                  :src="relatedProduct.imageUrl"
                  :alt="relatedProduct.name"
                  class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                >
                <div v-else class="w-full h-full flex items-center justify-center bg-gradient-primary">
                  <svg class="w-12 h-12 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                  </svg>
                </div>
              </div>
              <h3 class="text-lg font-semibold text-navy-900 mb-2">{{ relatedProduct.name }}</h3>
              <p v-if="relatedProduct.shortDescription" class="text-gray-600 text-sm mb-4">
                {{ relatedProduct.shortDescription }}
              </p>
              <Button variant="secondary" size="sm" full-width>
                Learn More
              </Button>
            </Card>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
const route = useRoute()
const slug = route.params.slug as string

// SEO Meta Tags (will be updated dynamically when product loads)
useSeoMeta({
  title: 'Loading Product...',
  description: 'Loading product information...'
})

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Fetch product data
const { data: product, pending, error } = await useFetch(`${apiBaseUrl}/api/public/products/${slug}`)

// Fetch related products (same category)
const { data: relatedProductsData } = await useFetch(() => {
  if (!product.value?.category) return null
  return `${apiBaseUrl}/api/public/products/category/${product.value.category}`
})

const relatedProducts = computed(() => {
  if (!relatedProductsData.value) return []
  // Filter out current product and limit to 3
  return relatedProductsData.value
    .filter((p: any) => p.id !== product.value?.id)
    .slice(0, 3)
})

// Update SEO + inject JSON-LD when product loads
watch(product, (newProduct) => {
  if (newProduct) {
    // Prefer admin-set meta fields, fall back to product content
    const title = (newProduct as any).metaTitle || `${newProduct.name} | Custom Packaging | MagerPack`
    const description = (newProduct as any).metaDescription
      || newProduct.shortDescription
      || newProduct.description
      || `Professional ${newProduct.name} manufacturing services. Get factory-direct pricing and rapid sampling support.`

    useSeoMeta({
      title,
      ogTitle: title,
      description,
      ogDescription: description,
      keywords: (newProduct as any).metaKeywords || undefined,
      ogImage: newProduct.imageUrl || '/images/default-product-og.jpg',
      twitterCard: 'summary_large_image'
    })

    // JSON-LD Product structured data for Google rich results
    const images = [newProduct.imageUrl, ...((newProduct.additionalImages as string[]) || [])].filter(Boolean)
    const additionalProperty = Object.entries((newProduct.specifications as Record<string, string>) || {}).map(([k, v]) => ({
      '@type': 'PropertyValue', name: k, value: v
    }))
    if (newProduct.sizeRange) additionalProperty.push({ '@type': 'PropertyValue', name: 'Size Range', value: newProduct.sizeRange })
    if (newProduct.minimumOrderQuantity) additionalProperty.push({ '@type': 'PropertyValue', name: 'Minimum Order Quantity', value: String(newProduct.minimumOrderQuantity) })
    if (newProduct.leadTime) additionalProperty.push({ '@type': 'PropertyValue', name: 'Lead Time', value: newProduct.leadTime })

    useHead({
      script: [{
        type: 'application/ld+json',
        children: JSON.stringify({
          '@context': 'https://schema.org',
          '@type': 'Product',
          name: newProduct.name,
          description,
          image: images.length ? images : undefined,
          sku: newProduct.slug,
          category: newProduct.category,
          brand: {
            '@type': 'Organization',
            name: 'MagerPack',
            url: 'https://magerpack-website.vercel.app'
          },
          material: newProduct.materialOptions || undefined,
          additionalProperty: additionalProperty.length ? additionalProperty : undefined,
          audience: ((newProduct.targetIndustries as string[]) || []).map((i: string) => ({
            '@type': 'Audience',
            audienceType: i
          }))
        })
      }]
    })
  }
}, { immediate: true })

// Error handling
if (error.value) {
  throw createError({
    statusCode: 404,
    statusMessage: 'Product Not Found'
  })
}
</script>