<template>
  <div>
    <!-- Loading State -->
    <div v-if="pending" class="min-h-screen flex items-center justify-center">
      <div class="text-center">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto mb-4"></div>
        <p class="text-gray-600">Loading industry information...</p>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error || !industry" class="min-h-screen flex items-center justify-center">
      <div class="text-center">
        <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-3-5a9 9 0 11-6.364 0M15.364 7A9 9 0 017 7"/>
        </svg>
        <h1 class="text-2xl font-bold text-gray-900 mb-2">Industry Not Found</h1>
        <p class="text-gray-600 mb-6">Sorry, we couldn't find the industry page you're looking for.</p>
        <Button to="/industries" variant="primary">
          View All Industries
        </Button>
      </div>
    </div>

    <!-- Industry Content -->
    <div v-else>
      <!-- Industry Hero -->
      <section class="relative bg-navy-900 text-white overflow-hidden">
        <!-- Background Image -->
        <div v-if="industry.heroImageUrl" class="absolute inset-0">
          <img
            :src="industry.heroImageUrl"
            :alt="industry.name"
            class="w-full h-full object-cover opacity-30"
          >
          <div class="absolute inset-0 bg-navy-900 bg-opacity-60"></div>
        </div>

        <div class="relative container-custom py-20 lg:py-32">
          <div class="max-w-4xl">
            <div class="inline-flex items-center px-4 py-2 bg-primary-600 bg-opacity-20 text-primary-200 text-sm font-medium rounded-full mb-6">
              <svg v-if="industry.iconUrl" class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 24 24">
                <path :d="industry.iconUrl"/>
              </svg>
              <span>Industry Focus</span>
            </div>

            <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold mb-6">
              {{ industry.name }}
            </h1>

            <p v-if="industry.shortDescription" class="text-xl lg:text-2xl text-gray-300 mb-8 leading-relaxed">
              {{ industry.shortDescription }}
            </p>

            <div class="flex flex-col sm:flex-row gap-4">
              <Button variant="primary" size="lg" to="/contact-us">
                Get Custom Quote
              </Button>
              <Button variant="secondary" size="lg" @click="scrollToProducts">
                View Solutions
              </Button>
            </div>
          </div>
        </div>
      </section>

      <!-- Breadcrumb -->
      <section class="bg-gray-50 py-4">
        <div class="container-custom">
          <nav class="flex items-center space-x-2 text-sm">
            <NuxtLink to="/" class="text-gray-500 hover:text-primary-600">Home</NuxtLink>
            <span class="text-gray-400">/</span>
            <NuxtLink to="/industries" class="text-gray-500 hover:text-primary-600">Industries</NuxtLink>
            <span class="text-gray-400">/</span>
            <span class="text-gray-900 font-medium">{{ industry.name }}</span>
          </nav>
        </div>
      </section>

      <!-- Industry Overview -->
      <section class="section">
        <div class="container-custom">
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-16 items-start">
            <!-- Description -->
            <div class="space-y-8">
              <div>
                <h2 class="text-3xl font-bold text-navy-900 mb-6">Industry Overview</h2>
                <div class="prose prose-gray max-w-none">
                  <p v-if="industry.description" v-html="industry.description.replace(/\n/g, '<br>')"></p>
                  <p v-else class="text-gray-600">Detailed industry information coming soon.</p>
                </div>
              </div>

              <!-- Key Benefits -->
              <div v-if="industry.keyBenefits && industry.keyBenefits.length > 0">
                <h3 class="text-xl font-semibold text-navy-900 mb-4">Key Benefits for Your Industry</h3>
                <ul class="space-y-3">
                  <li v-for="benefit in industry.keyBenefits" :key="benefit" class="flex items-start gap-3">
                    <svg class="w-5 h-5 text-success-600 mt-1 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                    </svg>
                    <span class="text-gray-700">{{ benefit }}</span>
                  </li>
                </ul>
              </div>
            </div>

            <!-- Industry Stats/Highlights -->
            <div class="space-y-8">
              <!-- Common Challenges -->
              <Card v-if="industry.commonChallenges && industry.commonChallenges.length > 0" padding="lg">
                <h3 class="text-xl font-semibold text-navy-900 mb-4">
                  <svg class="w-6 h-6 text-orange-500 inline mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L5.082 16.5c-.77.833.192 2.5 1.732 2.5z"/>
                  </svg>
                  Common Challenges
                </h3>
                <ul class="space-y-2">
                  <li v-for="challenge in industry.commonChallenges" :key="challenge" class="text-gray-700 flex items-start gap-2">
                    <span class="w-2 h-2 bg-orange-400 rounded-full mt-2 flex-shrink-0"></span>
                    <span>{{ challenge }}</span>
                  </li>
                </ul>
              </Card>

              <!-- Our Solutions -->
              <Card v-if="industry.packagingSolutions && industry.packagingSolutions.length > 0" padding="lg" class="bg-primary-50 border-primary-200">
                <h3 class="text-xl font-semibold text-navy-900 mb-4">
                  <svg class="w-6 h-6 text-primary-600 inline mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  Our Solutions
                </h3>
                <ul class="space-y-3">
                  <li v-for="solution in industry.packagingSolutions" :key="solution" class="text-gray-700">
                    {{ solution }}
                  </li>
                </ul>
              </Card>

              <!-- Quick Contact -->
              <Card padding="lg" class="bg-navy-50 border-navy-200">
                <h3 class="text-lg font-semibold text-navy-900 mb-4">Need Expert Advice?</h3>
                <p class="text-gray-600 mb-6">Our packaging experts understand the unique requirements of your industry.</p>
                <div class="space-y-3">
                  <Button variant="primary" full-width to="/contact-us">
                    Speak with an Expert
                  </Button>
                  <div class="text-center">
                    <a href="tel:+8613728607297" class="text-sm text-primary-600 hover:underline">
                      Or call: +86 137 2860 7297
                    </a>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        </div>
      </section>

      <!-- Recommended Products -->
      <section ref="productsSection" class="section bg-gray-50">
        <div class="container-custom">
          <div class="text-center mb-12">
            <h2 class="text-3xl font-bold text-navy-900 mb-4">
              Recommended Packaging Solutions
            </h2>
            <p class="text-lead max-w-3xl mx-auto">
              Based on industry requirements and best practices, these packaging solutions are perfectly suited for {{ industry.name.toLowerCase() }}.
            </p>
          </div>

          <!-- Loading Products -->
          <div v-if="productsLoading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div v-for="i in 6" :key="i" class="animate-pulse">
              <div class="bg-gray-200 aspect-4-3 rounded-xl mb-4"></div>
              <div class="bg-gray-200 h-4 rounded mb-2"></div>
              <div class="bg-gray-200 h-3 rounded w-3/4"></div>
            </div>
          </div>

          <!-- Products Grid -->
          <div v-else-if="recommendedProducts && recommendedProducts.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <Card
              v-for="product in recommendedProducts"
              :key="product.id"
              hover
              padding="lg"
              class="group cursor-pointer"
              @click="$router.push(`/products/${product.slug}`)"
            >
              <div class="aspect-4-3 bg-gray-100 rounded-xl overflow-hidden mb-4">
                <img
                  v-if="product.imageUrl"
                  :src="product.imageUrl"
                  :alt="product.name"
                  class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                >
                <div v-else class="w-full h-full flex items-center justify-center bg-gradient-primary">
                  <svg class="w-12 h-12 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                  </svg>
                </div>
              </div>

              <div class="space-y-3">
                <h3 class="text-lg font-semibold text-navy-900 group-hover:text-primary-600 transition-colors">
                  {{ product.name }}
                </h3>

                <p v-if="product.shortDescription" class="text-sm text-gray-600 line-clamp-2">
                  {{ product.shortDescription }}
                </p>

                <div class="flex items-center gap-4 text-xs text-gray-500">
                  <div v-if="product.minimumOrderQuantity" class="flex items-center gap-1">
                    <svg class="w-3 h-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18"/>
                    </svg>
                    <span>{{ product.minimumOrderQuantity }} pcs min</span>
                  </div>
                  <div v-if="product.leadTime" class="flex items-center gap-1">
                    <svg class="w-3 h-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                    </svg>
                    <span>{{ product.leadTime }}</span>
                  </div>
                </div>

                <Button variant="secondary" size="sm" full-width>
                  Learn More
                </Button>
              </div>
            </Card>
          </div>

          <!-- No Products -->
          <div v-else class="text-center py-12">
            <p class="text-gray-600">No specific products available yet for this industry.</p>
            <Button variant="secondary" to="/products" class="mt-4">
              View All Products
            </Button>
          </div>
        </div>
      </section>

      <!-- Case Studies (if available) -->
      <section v-if="industry.caseStudies && industry.caseStudies.length > 0" class="section">
        <div class="container-custom">
          <h2 class="text-3xl font-bold text-navy-900 mb-12 text-center">Success Stories</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <Card
              v-for="(caseStudy, index) in industry.caseStudies"
              :key="index"
              padding="lg"
              class="bg-white border-l-4 border-l-primary-600"
            >
              <div class="flex items-start gap-4">
                <div class="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center flex-shrink-0">
                  <svg class="w-6 h-6 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                </div>
                <div class="flex-1">
                  <h3 class="text-lg font-semibold text-navy-900 mb-3">Case Study {{ index + 1 }}</h3>
                  <p class="text-gray-600">{{ caseStudy }}</p>
                </div>
              </div>
            </Card>
          </div>
        </div>
      </section>

      <!-- CTA Section -->
      <section class="section bg-primary-600 text-white">
        <div class="container-custom text-center">
          <h2 class="text-3xl md:text-4xl font-bold mb-6">
            Ready to Transform Your {{ industry.name }} Packaging?
          </h2>
          <p class="text-xl mb-8 opacity-90 max-w-3xl mx-auto">
            Get expert guidance and custom packaging solutions designed specifically for your industry's unique requirements.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <Button variant="secondary" size="lg" to="/contact-us">
              Start Your Project
            </Button>
            <Button variant="ghost" size="lg" to="/products">
              Browse All Products
            </Button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
const route = useRoute()
const slug = route.params.slug as string

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Refs
const productsSection = ref<HTMLElement>()

// SEO Meta Tags (will be updated dynamically when industry loads)
useSeoMeta({
  title: 'Loading Industry...',
  description: 'Loading industry information...'
})

// Fetch industry data — graceful null on backend failure
const { data: industry, pending, error } = await useAsyncData(
  `industry-${slug}`,
  () => $fetch<any>(`${apiBaseUrl}/api/public/industries/${slug}`).catch(() => null),
  { default: () => null }
)

// Fetch recommended products for this industry — graceful empty on failure
const { data: recommendedProducts, pending: productsLoading } = await useAsyncData(
  `industry-products-${slug}`,
  () => {
    if (!industry.value?.name) return Promise.resolve([])
    return $fetch<any[]>(`${apiBaseUrl}/api/public/products/industry/${industry.value.name}`).catch(() => [])
  },
  { default: () => [] as any[] }
)

// Update SEO when industry loads
watch(industry, (newIndustry) => {
  if (newIndustry) {
    useSeoMeta({
      title: `${newIndustry.metaTitle || `${newIndustry.name} Packaging Solutions`} | MagerPack`,
      ogTitle: `${newIndustry.metaTitle || `${newIndustry.name} Packaging Solutions`} | MagerPack`,
      description: newIndustry.metaDescription || newIndustry.shortDescription || `Specialized packaging solutions for ${newIndustry.name.toLowerCase()}. Get expert guidance and custom solutions designed for your industry.`,
      ogDescription: newIndustry.metaDescription || newIndustry.shortDescription || `Specialized packaging solutions for ${newIndustry.name.toLowerCase()}. Get expert guidance and custom solutions designed for your industry.`,
      ogImage: newIndustry.heroImageUrl || '/images/industry-og.jpg',
      twitterCard: 'summary_large_image',
      keywords: newIndustry.seoKeywords ? newIndustry.seoKeywords.join(', ') : `${newIndustry.name}, packaging, custom packaging, manufacturing`
    })
  }
}, { immediate: true })

// Methods
const scrollToProducts = () => {
  productsSection.value?.scrollIntoView({ behavior: 'smooth' })
}

// Error handling
if (error.value) {
  throw createError({
    statusCode: 404,
    statusMessage: 'Industry Not Found'
  })
}
</script>