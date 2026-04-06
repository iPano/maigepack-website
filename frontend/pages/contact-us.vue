<template>
  <div>
    <!-- Hero Section -->
    <section class="bg-navy-900 text-white py-16 lg:py-24">
      <div class="container-custom">
        <div class="max-w-4xl mx-auto text-center">
          <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold mb-6">
            Get Your Free Quote
          </h1>
          <p class="text-xl lg:text-2xl text-gray-300 mb-8">
            Ready to start your packaging project? Our experts will respond within 24 hours
            with a detailed quote and recommendations.
          </p>
          <div class="flex flex-wrap justify-center gap-8 text-sm">
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
              <span>24-Hour Response</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
              <span>Free Samples Available</span>
            </div>
            <div class="flex items-center space-x-2">
              <svg class="w-5 h-5 text-primary-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"/>
              </svg>
              <span>Factory Direct Pricing</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Contact Form Section -->
    <section class="section">
      <div class="container-custom">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-16">
          <!-- Contact Form -->
          <div>
            <Card padding="lg">
              <h2 class="text-2xl font-bold text-navy-900 mb-6">Request Your Quote</h2>

              <form @submit.prevent="submit" class="space-y-6">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <Input
                    v-model="form.name"
                    label="Full Name"
                    type="text"
                    placeholder="Enter your name"
                    required
                    :error="errors.name"
                  />

                  <Input
                    v-model="form.email"
                    label="Email Address"
                    type="email"
                    placeholder="your@company.com"
                    required
                    :error="errors.email"
                  />
                </div>

                <Input
                  v-model="form.phone"
                  label="Phone Number / WhatsApp"
                  type="tel"
                  placeholder="+1 (555) 123-4567"
                  help="Optional - for faster communication"
                />

                <Input
                  v-model="form.company"
                  label="Company Name"
                  type="text"
                  placeholder="Your Company Ltd."
                />

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div>
                    <label class="form-label">Product Type</label>
                    <select
                      v-model="form.productType"
                      class="form-input"
                    >
                      <option value="">Select a product type</option>
                      <option value="rigid-boxes">Rigid Boxes</option>
                      <option value="folding-boxes">Folding Boxes</option>
                      <option value="display-boxes">Display Boxes</option>
                      <option value="packaging-sleeves">Packaging Sleeves</option>
                      <option value="custom-inserts">Custom Inserts</option>
                      <option value="other">Other / Not Sure</option>
                    </select>
                  </div>

                  <Input
                    v-model="form.quantity"
                    label="Estimated Quantity"
                    type="text"
                    placeholder="e.g., 1000 pieces"
                    help="Approximate quantity needed"
                  />
                </div>

                <Input
                  v-model="form.message"
                  label="Project Details"
                  tag="textarea"
                  :rows="6"
                  placeholder="Please describe your packaging requirements, dimensions, materials, printing needs, timeline, and any other specific requirements..."
                  required
                  :error="errors.message"
                  help="The more details you provide, the more accurate our quote will be."
                />

                <Button
                  type="submit"
                  variant="primary"
                  size="lg"
                  full-width
                  :loading="submitting"
                >
                  {{ submitting ? 'Sending Request...' : 'Get Free Quote' }}
                </Button>

                <p v-if="resultMessage" class="text-success-600 text-center font-medium">
                  {{ resultMessage }}
                </p>

                <p v-if="errorMessage" class="text-red-600 text-center">
                  {{ errorMessage }}
                </p>

                <p class="text-sm text-gray-500 text-center">
                  By submitting this form, you agree to our
                  <NuxtLink to="/privacy-policy" class="text-primary-600 hover:underline">Privacy Policy</NuxtLink>.
                  We respect your privacy and will never share your information.
                </p>
              </form>
            </Card>
          </div>

          <!-- Contact Information -->
          <div class="space-y-8">
            <!-- Contact Details -->
            <Card padding="lg">
              <h3 class="text-xl font-semibold text-navy-900 mb-6">Contact Information</h3>
              <div class="space-y-6">
                <div class="flex items-start space-x-4">
                  <div class="w-10 h-10 bg-primary-100 rounded-lg flex items-center justify-center flex-shrink-0">
                    <svg class="w-5 h-5 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                    </svg>
                  </div>
                  <div>
                    <h4 class="font-medium text-navy-800">Email</h4>
                    <a href="mailto:sales@magerpack.com" class="text-gray-600 hover:text-primary-600">
                      sales@magerpack.com
                    </a>
                    <p class="text-sm text-gray-500">Primary contact for quotes</p>
                  </div>
                </div>

                <div class="flex items-start space-x-4">
                  <div class="w-10 h-10 bg-success-100 rounded-lg flex items-center justify-center flex-shrink-0">
                    <svg class="w-5 h-5 text-success-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                    </svg>
                  </div>
                  <div>
                    <h4 class="font-medium text-navy-800">Phone / WhatsApp</h4>
                    <a href="tel:+8613728607297" class="text-gray-600 hover:text-primary-600">
                      +86 137 2860 7297
                    </a>
                    <p class="text-sm text-gray-500">Available 9 AM - 6 PM CST</p>
                  </div>
                </div>

                <div class="flex items-start space-x-4">
                  <div class="w-10 h-10 bg-accent-100 rounded-lg flex items-center justify-center flex-shrink-0">
                    <svg class="w-5 h-5 text-accent-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                    </svg>
                  </div>
                  <div>
                    <h4 class="font-medium text-navy-800">Factory Location</h4>
                    <p class="text-gray-600">Shenzhen, Guangdong Province</p>
                    <p class="text-sm text-gray-500">Manufacturing facility</p>
                  </div>
                </div>
              </div>
            </Card>

            <!-- Response Time -->
            <Card padding="lg">
              <h3 class="text-xl font-semibold text-navy-900 mb-4">Our Response Process</h3>
              <div class="space-y-4">
                <div class="flex items-center space-x-3">
                  <div class="w-8 h-8 bg-primary-600 text-white rounded-full flex items-center justify-center text-sm font-bold">1</div>
                  <div>
                    <p class="font-medium">Immediate Confirmation</p>
                    <p class="text-sm text-gray-500">You'll receive an automated confirmation</p>
                  </div>
                </div>
                <div class="flex items-center space-x-3">
                  <div class="w-8 h-8 bg-primary-600 text-white rounded-full flex items-center justify-center text-sm font-bold">2</div>
                  <div>
                    <p class="font-medium">Expert Review (2-4 hours)</p>
                    <p class="text-sm text-gray-500">Our team reviews your requirements</p>
                  </div>
                </div>
                <div class="flex items-center space-x-3">
                  <div class="w-8 h-8 bg-primary-600 text-white rounded-full flex items-center justify-center text-sm font-bold">3</div>
                  <div>
                    <p class="font-medium">Detailed Quote (within 24 hours)</p>
                    <p class="text-sm text-gray-500">Complete pricing and recommendations</p>
                  </div>
                </div>
              </div>
            </Card>

            <!-- Why Choose Us -->
            <Card padding="lg" class="bg-gray-50">
              <h3 class="text-xl font-semibold text-navy-900 mb-4">Why Choose MagerPack?</h3>
              <ul class="space-y-3">
                <li class="flex items-center space-x-3">
                  <svg class="w-5 h-5 text-success-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span class="text-gray-700">Over 20 years of packaging experience</span>
                </li>
                <li class="flex items-center space-x-3">
                  <svg class="w-5 h-5 text-success-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span class="text-gray-700">ISO 9001 certified manufacturing</span>
                </li>
                <li class="flex items-center space-x-3">
                  <svg class="w-5 h-5 text-success-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span class="text-gray-700">Factory-direct pricing with no middleman</span>
                </li>
                <li class="flex items-center space-x-3">
                  <svg class="w-5 h-5 text-success-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span class="text-gray-700">Free samples and rapid prototyping</span>
                </li>
              </ul>
            </Card>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'

// SEO Meta Tags
useSeoMeta({
  title: 'Get Free Quote - Custom Packaging | MagerPack',
  ogTitle: 'Get Free Quote - Custom Packaging | MagerPack',
  description: 'Get a free quote for custom packaging solutions. Expert team responds within 24 hours with detailed pricing and recommendations.',
  ogDescription: 'Get a free quote for custom packaging solutions. Expert team responds within 24 hours with detailed pricing and recommendations.',
  ogImage: '/images/contact-og.jpg',
  twitterCard: 'summary_large_image'
})

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

const submitting = ref(false)
const resultMessage = ref<string>('')
const errorMessage = ref<string>('')

const errors = reactive({
  name: '',
  email: '',
  message: ''
})

const form = reactive({
  type: 'contact',
  name: '',
  email: '',
  phone: '',
  company: '',
  productType: '',
  quantity: '',
  message: ''
})

const validateForm = () => {
  // Reset errors
  errors.name = ''
  errors.email = ''
  errors.message = ''

  let isValid = true

  if (!form.name.trim()) {
    errors.name = 'Name is required'
    isValid = false
  }

  if (!form.email.trim()) {
    errors.email = 'Email is required'
    isValid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = 'Please enter a valid email address'
    isValid = false
  }

  if (!form.message.trim()) {
    errors.message = 'Please describe your packaging requirements'
    isValid = false
  }

  return isValid
}

async function submit() {
  if (!validateForm()) {
    return
  }

  submitting.value = true
  resultMessage.value = ''
  errorMessage.value = ''

  try {
    await $fetch(`${apiBaseUrl}/api/public/forms/quote`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: form
    })

    resultMessage.value = 'Thank you! Your quote request has been submitted. Our team will respond within 24 hours with detailed pricing and recommendations.'

    // Reset form
    form.name = ''
    form.email = ''
    form.phone = ''
    form.company = ''
    form.productType = ''
    form.quantity = ''
    form.message = ''
  } catch (e: any) {
    console.error('Form submission error:', e)
    errorMessage.value = 'Sorry, there was an error submitting your request. Please try again or contact us directly at sales@magerpack.com'
  } finally {
    submitting.value = false
  }
}
</script>

