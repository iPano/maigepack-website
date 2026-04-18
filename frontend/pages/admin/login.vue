<template>
  <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
    <div class="bg-white rounded-xl shadow-lg w-full max-w-md p-8">
      <div class="text-center mb-8">
        <h1 class="text-2xl font-bold text-navy-900">Admin Panel</h1>
        <p class="text-gray-500 mt-2">Sign in to manage products</p>
      </div>

      <form @submit.prevent="handleLogin" class="space-y-5">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700 mb-1">
            Username
          </label>
          <input
            id="username"
            v-model="form.username"
            type="text"
            autocomplete="username"
            required
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
            placeholder="admin"
          />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 mb-1">
            Password
          </label>
          <input
            id="password"
            v-model="form.password"
            type="password"
            autocomplete="current-password"
            required
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
            placeholder="••••••••"
          />
        </div>

        <div v-if="errorMessage" class="bg-red-50 border border-red-200 text-red-700 rounded-lg px-4 py-3 text-sm">
          {{ errorMessage }}
        </div>

        <button
          type="submit"
          :disabled="loading"
          class="w-full bg-primary-600 hover:bg-primary-700 disabled:opacity-50 disabled:cursor-not-allowed text-white font-semibold py-2.5 px-4 rounded-lg transition-colors"
        >
          <span v-if="loading" class="flex items-center justify-center gap-2">
            <svg class="animate-spin h-4 w-4" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
            </svg>
            Signing in...
          </span>
          <span v-else>Sign In</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
definePageMeta({ layout: 'admin' })
useSeo({ title: 'Admin Login | MaigePack', description: 'Admin login page.', noindex: true })

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

const form = reactive({ username: '', password: '' })
const loading = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  loading.value = true
  errorMessage.value = ''

  try {
    const data = await $fetch<{ token: string }>(`${apiBaseUrl}/api/admin/auth/login`, {
      method: 'POST',
      body: { username: form.username, password: form.password }
    })

    if (!data?.token) {
      errorMessage.value = `Login failed: response was ${JSON.stringify(data)}`
      return
    }

    localStorage.setItem('adminToken', data.token)
    await navigateTo('/admin/products')
  } catch (error: unknown) {
    const status = (error as { statusCode?: number; status?: number })?.statusCode
      ?? (error as { statusCode?: number; status?: number })?.status
    if (status === 401) {
      errorMessage.value = 'Invalid credentials. Please try again.'
    } else {
      errorMessage.value = `Error ${status ?? 'unknown'}: could not reach the server. Check console for details.`
      console.error('[login]', error)
    }
  } finally {
    loading.value = false
  }
}
</script>
