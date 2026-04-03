<template>
  <component
    :is="computedTag"
    :type="isButton ? type : undefined"
    :href="isAnchor ? href : undefined"
    :to="isLink ? to : undefined"
    :disabled="disabled"
    :class="buttonClasses"
    @click="$emit('click', $event)"
  >
    <Icon v-if="iconLeft" :name="iconLeft" class="w-4 h-4 mr-2" />
    <slot />
    <Icon v-if="iconRight" :name="iconRight" class="w-4 h-4 ml-2" />
    <div v-if="loading" class="ml-2">
      <svg class="animate-spin -ml-1 mr-2 h-4 w-4" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
      </svg>
    </div>
  </component>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { resolveComponent } from 'vue'

interface Props {
  variant?: 'primary' | 'secondary' | 'accent' | 'ghost' | 'danger'
  size?: 'sm' | 'md' | 'lg' | 'xl'
  tag?: 'button' | 'a' | 'NuxtLink'
  type?: 'button' | 'submit' | 'reset'
  href?: string
  to?: string
  disabled?: boolean
  loading?: boolean
  fullWidth?: boolean
  iconLeft?: string
  iconRight?: string
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'primary',
  size: 'md',
  tag: 'button',
  type: 'button',
  disabled: false,
  loading: false,
  fullWidth: false
})

// resolveComponent('NuxtLink') returns the actual component object.
// Passing the string 'NuxtLink' to <component :is> does NOT work in
// Nuxt 3 — Vue cannot resolve global components by string at runtime
// inside <component :is>; you must pass the component definition itself.
const NuxtLinkComponent = resolveComponent('NuxtLink')

const computedTag = computed(() => {
  if (props.tag === 'NuxtLink') return NuxtLinkComponent
  if (props.tag !== 'button') return props.tag
  if (props.to) return NuxtLinkComponent
  if (props.href) return 'a'
  return 'button'
})

// Helpers for the template so we don't compare against a component object
const isButton = computed(() => computedTag.value === 'button')
const isAnchor = computed(() => computedTag.value === 'a')
const isLink = computed(() => !isButton.value && !isAnchor.value)

defineEmits<{
  click: [event: MouseEvent]
}>()

const buttonClasses = computed(() => {
  const baseClasses = 'inline-flex items-center justify-center font-medium rounded-lg transition-all duration-200 focus:ring-4 focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed'

  const variantClasses = {
    primary: 'bg-primary-600 text-white hover:bg-primary-700 focus:ring-primary-300 shadow-md hover:shadow-lg',
    secondary: 'bg-white text-navy-700 border-2 border-navy-200 hover:border-navy-300 hover:bg-navy-50 focus:ring-navy-300',
    accent: 'bg-accent-600 text-white hover:bg-accent-700 focus:ring-accent-300 shadow-md hover:shadow-lg',
    ghost: 'text-navy-700 hover:bg-navy-100 focus:ring-navy-300',
    danger: 'bg-red-600 text-white hover:bg-red-700 focus:ring-red-300 shadow-md hover:shadow-lg'
  }

  const sizeClasses = {
    sm: 'px-4 py-2 text-sm',
    md: 'px-6 py-3 text-sm',
    lg: 'px-8 py-4 text-base',
    xl: 'px-10 py-5 text-lg'
  }

  const widthClass = props.fullWidth ? 'w-full' : ''
  const loadingClass = props.loading ? 'cursor-wait' : ''

  return [
    baseClasses,
    variantClasses[props.variant],
    sizeClasses[props.size],
    widthClass,
    loadingClass
  ].filter(Boolean).join(' ')
})
</script>
