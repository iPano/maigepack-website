<template>
  <div>
    <!-- Hero Section -->
    <section class="bg-navy-900 text-white py-16 lg:py-24">
      <div class="container-custom">
        <div class="max-w-4xl mx-auto text-center">
          <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold mb-6">
            Industries We Serve
          </h1>
          <p class="text-xl lg:text-2xl text-gray-300 mb-8">
            Specialized packaging solutions tailored to meet the unique requirements of your industry.
            From electronics to cosmetics, we understand what it takes to protect and present your products.
          </p>
          <div class="flex flex-wrap justify-center gap-8 text-sm">
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"/>
              </svg>
              <span>Industry Expertise</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
              <span>Compliance Standards</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
              </svg>
              <span>Custom Solutions</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Industries Grid -->
    <section class="section">
      <div class="container-custom">
        <!-- Loading State -->
        <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div v-for="i in 6" :key="i" class="animate-pulse">
            <div class="bg-gray-200 h-48 rounded-xl mb-4"></div>
            <div class="bg-gray-200 h-6 rounded mb-2"></div>
            <div class="bg-gray-200 h-4 rounded w-3/4 mb-2"></div>
            <div class="bg-gray-200 h-4 rounded w-1/2"></div>
          </div>
        </div>

        <!-- No Industries -->
        <div v-else-if="!industries || industries.length === 0" class="text-center py-16">
          <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"/>
          </svg>
          <h3 class="text-xl font-bold text-gray-900 mb-2">Industries Coming Soon</h3>
          <p class="text-gray-600 mb-6">We're adding detailed industry information. Check back soon!</p>
          <Button variant="primary" to="/contact-us">
            Discuss Your Industry Needs
          </Button>
        </div>

        <!-- Industries Grid -->
        <div v-else class="space-y-12">
          <div class="text-center">
            <p class="text-gray-600">
              Serving {{ industries.length }} specialized industries with custom packaging solutions
            </p>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <Card
              v-for="industry in industries"
              :key="industry.id"
              hover
              padding="none"
              class="group cursor-pointer overflow-hidden"
              @click="$router.push(`/industries/${industry.slug}`)"
            >
              <!-- Industry Image/Hero -->
              <div class="aspect-16-9 bg-gray-100 relative overflow-hidden">
                <img
                  v-if="industry.heroImageUrl"
                  :src="industry.heroImageUrl"
                  :alt="industry.name"
                  class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                >
                <div v-else class="w-full h-full flex items-center justify-center bg-gradient-to-br from-primary-500 to-primary-600">
                  <svg class="w-16 h-16 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"/>
                  </svg>
                </div>

                <!-- Gradient Overlay -->
                <div class="absolute inset-0 bg-gradient-to-t from-navy-900/60 via-transparent to-transparent"></div>

                <!-- Industry Icon (if available) -->
                <div v-if="industry.iconUrl" class="absolute top-4 right-4 w-12 h-12 bg-white bg-opacity-20 backdrop-blur-sm rounded-lg flex items-center justify-center">
                  <svg class="w-6 h-6 text-white" fill="currentColor" viewBox="0 0 24 24">
                    <path :d="industry.iconUrl"/>
                  </svg>
                </div>
              </div>

              <!-- Industry Content -->
              <div class="p-6 space-y-4">
                <div>
                  <h3 class="text-xl font-bold text-navy-900 group-hover:text-primary-600 transition-colors mb-2">
                    {{ industry.name }}
                  </h3>
                  <p v-if="industry.shortDescription" class="text-gray-600 line-clamp-2">
                    {{ industry.shortDescription }}
                  </p>
                </div>

                <!-- Key Benefits Preview -->
                <div v-if="industry.keyBenefits && industry.keyBenefits.length > 0" class="space-y-2">
                  <h4 class="text-sm font-semibold text-navy-800">Key Benefits:</h4>
                  <ul class="space-y-1">
                    <li
                      v-for="benefit in industry.keyBenefits.slice(0, 2)"
                      :key="benefit"
                      class="flex items-start gap-2 text-sm text-gray-600"
                    >
                      <svg class="w-4 h-4 text-success-500 mt-0.5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                      </svg>
                      <span class="line-clamp-1">{{ benefit }}</span>
                    </li>
                  </ul>
                  <p v-if="industry.keyBenefits.length > 2" class="text-xs text-gray-500">
                    +{{ industry.keyBenefits.length - 2 }} more benefits
                  </p>
                </div>

                <!-- Recommended Products -->
                <div v-if="industry.recommendedProductTypes && industry.recommendedProductTypes.length > 0" class="space-y-2">
                  <h4 class="text-sm font-semibold text-navy-800">Recommended Solutions:</h4>
                  <div class="flex flex-wrap gap-1">
                    <Badge
                      v-for="productType in industry.recommendedProductTypes.slice(0, 3)"
                      :key="productType"
                      variant="secondary"
                      size="sm"
                    >
                      {{ productType }}
                    </Badge>
                    <span v-if="industry.recommendedProductTypes.length > 3" class="text-xs text-gray-500 px-2 py-1">
                      +{{ industry.recommendedProductTypes.length - 3 }}
                    </span>
                  </div>
                </div>

                <!-- Action -->
                <div class="pt-2">
                  <Button variant="secondary" size="sm" full-width>
                    Explore Solutions
                    <svg class="w-4 h-4 ml-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3"/>
                    </svg>
                  </Button>
                </div>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </section>

    <!-- Why Choose Us by Industry -->
    <section class="section bg-gray-50">
      <div class="container-custom">
        <div class="text-center mb-16">
          <h2 class="text-3xl md:text-4xl font-bold text-navy-900 mb-6">
            Industry-Specific Expertise
          </h2>
          <p class="text-lead max-w-3xl mx-auto">
            Each industry has unique packaging requirements. Our specialized approach ensures your packaging
            meets industry standards while protecting and enhancing your brand.
          </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Compliance & Standards -->
          <Card padding="lg" class="text-center group hover:bg-primary-50 transition-colors">
            <div class="w-16 h-16 bg-gradient-to-br from-blue-500 to-blue-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform">
              <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-navy-900 mb-4">Compliance & Standards</h3>
            <p class="text-gray-600">
              We understand industry-specific regulations and standards, ensuring your packaging meets all compliance requirements.
            </p>
          </Card>

          <!-- Material Expertise -->
          <Card padding="lg" class="text-center group hover:bg-primary-50 transition-colors">
            <div class="w-16 h-16 bg-gradient-to-br from-green-500 to-green-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform">
              <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19.428 15.428a2 2 0 00-1.022-.547l-2.387-.477a6 6 0 00-3.86.517l-.318.158a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.806.547M8 4h8l-1 1v5.172a2 2 0 00.586 1.414l5 5c1.26 1.26.367 3.414-1.415 3.414H4.828c-1.782 0-2.674-2.154-1.414-3.414l5-5A2 2 0 009 10.172V5L8 4z"/>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-navy-900 mb-4">Material Expertise</h3>
            <p class="text-gray-600">
              Specialized knowledge of materials that work best for each industry, from food-grade to electronics-safe options.
            </p>
          </Card>

          <!-- Custom Solutions -->
          <Card padding="lg" class="text-center group hover:bg-primary-50 transition-colors">
            <div class="w-16 h-16 bg-gradient-to-br from-purple-500 to-purple-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform">
              <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 4a2 2 0 114 0v1a1 1 0 001 1h3a1 1 0 011 1v3a1 1 0 01-1 1h-1a2 2 0 100 4h1a1 1 0 011 1v3a1 1 0 01-1 1h-3a1 1 0 01-1-1v-1a2 2 0 10-4 0v1a1 1 0 01-1 1H7a1 1 0 01-1-1v-3a1 1 0 00-1-1H4a1 1 0 01-1-1V9a1 1 0 011-1h1a2 2 0 100-4H4a1 1 0 01-1-1V4a1 1 0 011-1h3a1 1 0 001-1v-1z"/>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-navy-900 mb-4">Custom Solutions</h3>
            <p class="text-gray-600">
              Tailored packaging designs that address the specific challenges and opportunities of your industry.
            </p>
          </Card>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="section bg-primary-600 text-white">
      <div class="container-custom text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-6">
          Don't See Your Industry?
        </h2>
        <p class="text-xl mb-8 opacity-90 max-w-3xl mx-auto">
          We work with businesses across many industries. Our packaging experts can create custom solutions
          for your specific requirements, no matter your industry.
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <Button variant="secondary" size="lg" to="/contact-us">
            Discuss Your Needs
          </Button>
          <Button variant="ghost" size="lg" to="/products">
            Browse All Products
          </Button>
        </div>

        <div class="mt-12 pt-8 border-t border-primary-500 border-opacity-30">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-100 mb-2">500+</div>
              <div class="text-primary-200">Industries Served</div>
            </div>
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-100 mb-2">20+</div>
              <div class="text-primary-200">Years Experience</div>
            </div>
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-100 mb-2">10K+</div>
              <div class="text-primary-200">Projects Completed</div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
// SEO Meta Tags
useSeo({
  title: 'Industries We Serve | MagerPack Custom Packaging',
  description: 'Specialized packaging solutions for electronics, cosmetics, jewelry, fragrance, toys, and more. Industry expertise with compliance standards and custom solutions.',
  ogImage: '/images/industries-og.jpg',
  path: '/industries'
})

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Fetch all active industries
const { data: industries, pending, error } = await useFetch(`${apiBaseUrl}/api/public/industries`)

// Error handling
if (error.value) {
  console.error('Failed to load industries:', error.value)
}
</script>