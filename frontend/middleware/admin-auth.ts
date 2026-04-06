export default defineNuxtRouteMiddleware(() => {
  // Only runs client-side (SPA mode)
  if (import.meta.server) return

  const token = localStorage.getItem('adminToken')

  if (!token) {
    return navigateTo('/admin/login')
  }

  // Decode JWT payload (base64url) and check exp
  try {
    const parts = token.split('.')
    if (parts.length !== 3) throw new Error('Invalid JWT structure')

    const payload = JSON.parse(atob(parts[1].replace(/-/g, '+').replace(/_/g, '/')))
    const now = Math.floor(Date.now() / 1000)

    if (!payload.exp || payload.exp < now) {
      localStorage.removeItem('adminToken')
      return navigateTo('/admin/login')
    }
  } catch {
    localStorage.removeItem('adminToken')
    return navigateTo('/admin/login')
  }
})
