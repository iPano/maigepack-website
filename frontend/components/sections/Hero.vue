<template>
  <section class="relative bg-gradient-navy overflow-hidden">
    <!-- Background Pattern -->
    <div class="absolute inset-0 opacity-10">
      <div class="absolute top-0 left-0 w-full h-full" style="background-image: url('data:image/svg+xml,%3Csvg width=%2260%22 height=%2260%22 viewBox=%220 0 60 60%22 xmlns=%22http://www.w3.org/2000/svg%22%3E%3Cg fill=%22none%22 fill-rule=%22evenodd%22%3E%3Cg fill=%22%239C92AC%22 fill-opacity=%220.1%22%3E%3Ccircle cx=%2230%22 cy=%2230%22 r=%222%22/%3E%3C/g%3E%3C/g%3E%3C/svg%3E')"></div>
    </div>

    <div class="relative container-custom py-20 lg:py-32">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 lg:gap-16 items-center">
        <!-- Content -->
        <div class="text-white">
          <div class="inline-flex items-center px-4 py-2 bg-primary-600 bg-opacity-20 text-primary-200 text-sm font-medium rounded-full mb-6">
            <span class="w-2 h-2 bg-primary-400 rounded-full mr-2"></span>
            Trusted Since 2004
          </div>

          <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold leading-tight mb-6 text-shadow-lg">
            Custom Packaging
            <span class="block text-primary-400">Solutions for</span>
            <span class="block">Your Brand</span>
          </h1>

          <p class="text-xl lg:text-2xl text-gray-300 mb-8 leading-relaxed">
            Professional packaging manufacturer specializing in premium rigid boxes, folding cartons,
            and display solutions. Get factory-direct pricing and rapid sampling support.
          </p>

          <div class="flex flex-col sm:flex-row gap-4 mb-8">
            <Button variant="primary" size="lg" to="/contact-us">
              Get Free Quote in 24 Hours
            </Button>
            <Button variant="secondary" size="lg" to="/products">
              View Products
            </Button>
          </div>

          <!-- Key Stats -->
          <div class="grid grid-cols-3 gap-6 pt-8 border-t border-navy-700">
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-400">20+</div>
              <div class="text-sm text-gray-400">Years Experience</div>
            </div>
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-400">10K+</div>
              <div class="text-sm text-gray-400">Projects Completed</div>
            </div>
            <div>
              <div class="text-2xl lg:text-3xl font-bold text-primary-400">500+</div>
              <div class="text-sm text-gray-400">Happy Clients</div>
            </div>
          </div>
        </div>

        <!-- Hero Image/Graphic -->
        <div class="relative">
          <div class="relative z-10">
            <!-- Placeholder for hero image - replace with actual product images -->
            <div class="grid grid-cols-2 gap-4">
              <div class="space-y-4">
                <div class="bg-white rounded-lg shadow-large p-6 transform rotate-3 hover:rotate-0 transition-transform duration-300">
                  <div class="w-full h-32 bg-gradient-to-br from-primary-100 to-primary-200 rounded-lg flex items-center justify-center mb-4">
                    <svg class="w-12 h-12 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                    </svg>
                  </div>
                  <h3 class="font-semibold text-navy-800 mb-2">Rigid Boxes</h3>
                  <p class="text-sm text-gray-600">Premium packaging solutions</p>
                </div>

                <div class="bg-white rounded-lg shadow-large p-6 transform -rotate-2 hover:rotate-0 transition-transform duration-300">
                  <div class="w-full h-32 bg-gradient-to-br from-accent-100 to-accent-200 rounded-lg flex items-center justify-center mb-4">
                    <svg class="w-12 h-12 text-accent-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"/>
                    </svg>
                  </div>
                  <h3 class="font-semibold text-navy-800 mb-2">Display Boxes</h3>
                  <p class="text-sm text-gray-600">Retail display solutions</p>
                </div>
              </div>

              <div class="space-y-4 mt-8">
                <div class="bg-white rounded-lg shadow-large p-6 transform rotate-2 hover:rotate-0 transition-transform duration-300">
                  <div class="w-full h-32 bg-gradient-to-br from-success-100 to-success-200 rounded-lg flex items-center justify-center mb-4">
                    <svg class="w-12 h-12 text-success-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                    </svg>
                  </div>
                  <h3 class="font-semibold text-navy-800 mb-2">Folding Boxes</h3>
                  <p class="text-sm text-gray-600">Cost-effective packaging</p>
                </div>

                <div class="bg-white rounded-lg shadow-large p-6 transform -rotate-3 hover:rotate-0 transition-transform duration-300">
                  <div class="w-full h-32 bg-gradient-to-br from-yellow-100 to-yellow-200 rounded-lg flex items-center justify-center mb-4">
                    <svg class="w-12 h-12 text-yellow-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17v4a2 2 0 002 2h4a2 2 0 002-2v-4a2 2 0 00-2-2H9a2 2 0 00-2 2z"/>
                    </svg>
                  </div>
                  <h3 class="font-semibold text-navy-800 mb-2">Custom Inserts</h3>
                  <p class="text-sm text-gray-600">Protective foam inserts</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Decorative Elements -->
          <div class="absolute -top-4 -right-4 w-24 h-24 bg-primary-400 rounded-full opacity-20 blur-xl"></div>
          <div class="absolute -bottom-8 -left-8 w-32 h-32 bg-accent-400 rounded-full opacity-20 blur-xl"></div>
        </div>
      </div>
    </div>

    <!-- Bottom Wave -->
    <div class="absolute bottom-0 left-0 right-0">
      <svg class="w-full h-auto text-white" viewBox="0 0 1440 120" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M0 120L48 110C96 100 192 80 288 70C384 60 480 60 576 65C672 70 768 80 864 85C960 90 1056 90 1152 85C1248 80 1344 70 1392 65L1440 60V120H1392C1344 120 1248 120 1152 120C1056 120 960 120 864 120C768 120 672 120 576 120C480 120 384 120 288 120C192 120 96 120 48 120H0V120Z" fill="currentColor"/>
      </svg>
    </div>
  </section>
</template>

<script setup lang="ts">
// Hero section logic can be added here if needed
</script>