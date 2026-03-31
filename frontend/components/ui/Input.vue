<template>
  <div class="form-group">
    <label v-if="label" :for="inputId" class="form-label">
      {{ label }}
      <span v-if="required" class="text-red-500 ml-1">*</span>
    </label>

    <div class="relative">
      <div v-if="iconLeft" class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
        <Icon :name="iconLeft" class="h-5 w-5 text-gray-400" />
      </div>

      <component
        :is="tag"
        :id="inputId"
        v-model="localValue"
        :type="type"
        :placeholder="placeholder"
        :disabled="disabled"
        :required="required"
        :class="inputClasses"
        :rows="tag === 'textarea' ? rows : undefined"
        v-bind="$attrs"
        @input="handleInput"
        @blur="handleBlur"
        @focus="handleFocus"
      />

      <div v-if="iconRight" class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
        <Icon :name="iconRight" class="h-5 w-5 text-gray-400" />
      </div>
    </div>

    <p v-if="error" class="form-error">
      {{ error }}
    </p>

    <p v-if="help && !error" class="text-sm text-gray-500 mt-1">
      {{ help }}
    </p>
  </div>
</template>

<script setup lang="ts">
interface Props {
  modelValue?: string | number
  label?: string
  type?: 'text' | 'email' | 'password' | 'number' | 'tel' | 'url' | 'search'
  tag?: 'input' | 'textarea'
  placeholder?: string
  disabled?: boolean
  required?: boolean
  error?: string
  help?: string
  iconLeft?: string
  iconRight?: string
  rows?: number
  size?: 'sm' | 'md' | 'lg'
}

const props = withDefaults(defineProps<Props>(), {
  type: 'text',
  tag: 'input',
  disabled: false,
  required: false,
  rows: 4,
  size: 'md'
})

const emit = defineEmits<{
  'update:modelValue': [value: string | number]
  input: [event: Event]
  blur: [event: FocusEvent]
  focus: [event: FocusEvent]
}>()

const inputId = ref(`input-${Math.random().toString(36).substr(2, 9)}`)

const localValue = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const inputClasses = computed(() => {
  const baseClasses = 'block w-full border rounded-lg transition-colors duration-200 placeholder-gray-500 focus:ring-2 focus:ring-primary-500 focus:border-primary-500'

  const sizeClasses = {
    sm: 'px-3 py-2 text-sm',
    md: 'px-4 py-3',
    lg: 'px-5 py-4 text-lg'
  }

  const stateClasses = props.error
    ? 'border-red-300 text-red-900 focus:ring-red-500 focus:border-red-500'
    : 'border-gray-300 text-gray-900'

  const disabledClasses = props.disabled
    ? 'bg-gray-50 text-gray-500 cursor-not-allowed'
    : 'bg-white'

  const paddingClasses = props.iconLeft ? 'pl-10' : ''
  const paddingRightClasses = props.iconRight ? 'pr-10' : ''

  return [
    baseClasses,
    sizeClasses[props.size],
    stateClasses,
    disabledClasses,
    paddingClasses,
    paddingRightClasses
  ].filter(Boolean).join(' ')
})

const handleInput = (event: Event) => {
  emit('input', event)
}

const handleBlur = (event: FocusEvent) => {
  emit('blur', event)
}

const handleFocus = (event: FocusEvent) => {
  emit('focus', event)
}
</script>