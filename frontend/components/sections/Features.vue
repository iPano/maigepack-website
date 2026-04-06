<template>
  <section class="section bg-gray-50">
    <div class="container-custom">
      <div class="text-center mb-16">
        <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold text-navy-900 mb-6">
          Why Choose MagerPack?
        </h2>
        <p class="text-lead max-w-3xl mx-auto">
          With over 20 years of experience in packaging manufacturing, we deliver quality solutions
          that protect your products and enhance your brand presence.
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <!-- Feature 1 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-primary rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Rapid Prototyping</h3>
              <p class="text-gray-600 mb-4">
                Get your packaging samples within 3-5 business days. Fast turnaround for urgent projects and design iterations.
              </p>
              <Badge variant="primary" size="sm">24-Hour Response</Badge>
            </div>
          </Card>
        </div>

        <!-- Feature 2 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-to-br from-success-500 to-success-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Quality Assured</h3>
              <p class="text-gray-600 mb-4">
                ISO 9001 certified facility with rigorous quality control processes. Every batch tested for durability and finish quality.
              </p>
              <Badge variant="success" size="sm">ISO 9001 Certified</Badge>
            </div>
          </Card>
        </div>

        <!-- Feature 3 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-to-br from-accent-500 to-accent-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Factory Direct Pricing</h3>
              <p class="text-gray-600 mb-4">
                No middleman markup. Direct from our factory in Shenzhen with transparent pricing and bulk discounts.
              </p>
              <Badge variant="accent" size="sm">Best Value</Badge>
            </div>
          </Card>
        </div>

        <!-- Feature 4 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-to-br from-yellow-500 to-yellow-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17v4a2 2 0 002 2h4a2 2 0 002-2v-4a2 2 0 00-2-2H9a2 2 0 00-2 2z"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Custom Solutions</h3>
              <p class="text-gray-600 mb-4">
                Tailored packaging solutions for any industry. From electronics to cosmetics, we create packages that fit perfectly.
              </p>
              <Badge variant="warning" size="sm">Fully Customizable</Badge>
            </div>
          </Card>
        </div>

        <!-- Feature 5 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-to-br from-blue-500 to-blue-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Global Shipping</h3>
              <p class="text-gray-600 mb-4">
                Worldwide delivery with reliable logistics partners. Track your shipment every step of the way.
              </p>
              <Badge variant="info" size="sm">Worldwide Delivery</Badge>
            </div>
          </Card>
        </div>

        <!-- Feature 6 -->
        <div class="group">
          <Card hover padding="lg">
            <div class="text-center">
              <div class="w-16 h-16 bg-gradient-to-br from-green-500 to-green-600 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"/>
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-navy-800 mb-4">Eco-Friendly Options</h3>
              <p class="text-gray-600 mb-4">
                Sustainable packaging materials and processes. FSC certified papers and recyclable materials available.
              </p>
              <Badge variant="success" size="sm">Eco-Friendly</Badge>
            </div>
          </Card>
        </div>
      </div>

      <!-- CTA Section -->
      <div class="text-center mt-16">
        <div class="bg-white rounded-2xl shadow-large p-8 lg:p-12 max-w-4xl mx-auto">
          <h3 class="text-2xl lg:text-3xl font-bold text-navy-900 mb-4">
            Ready to Get Started?
          </h3>
          <p class="text-gray-600 mb-8 text-lg">
            Get a free quote for your custom packaging needs. Our team will respond within 24 hours.
          </p>
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <Button variant="primary" size="lg" to="/contact-us">
              Request Free Quote
            </Button>
            <Button variant="secondary" size="lg" to="/products">
              Browse Products
            </Button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
// Features section logic can be added here if needed
</script>