export const useAdminApi = () => {
  const config = useRuntimeConfig()
  const apiBaseUrl = config.public.apiBaseUrl as string

  const getToken = (): string | null => {
    if (import.meta.server) return null
    return localStorage.getItem('adminToken')
  }

  const apiFetch = async <T>(
    path: string,
    options: Parameters<typeof $fetch>[1] = {}
  ): Promise<T> => {
    const token = getToken()

    try {
      return await $fetch<T>(`${apiBaseUrl}${path}`, {
        ...options,
        headers: {
          ...(options.headers as Record<string, string> | undefined),
          ...(token ? { Authorization: `Bearer ${token}` } : {})
        }
      })
    } catch (error: unknown) {
      // On 401: clear token and redirect to login
      if (
        error &&
        typeof error === 'object' &&
        'status' in error &&
        (error as { status: number }).status === 401
      ) {
        if (import.meta.client) {
          localStorage.removeItem('adminToken')
        }
        await navigateTo('/admin/login')
      }
      throw error
    }
  }

  return { apiFetch }
}
