import { ref } from 'vue'

export type ToastType = 'success' | 'error'

const visible = ref(false)
const message = ref('')
const type = ref<ToastType>('success')

let timer: ReturnType<typeof setTimeout> | undefined

export function useToast() {
  function show(msg: string, toastType: ToastType = 'success', duration = 3000) {
    if (timer) clearTimeout(timer)
    message.value = msg
    type.value = toastType
    visible.value = true
    timer = setTimeout(() => {
      visible.value = false
    }, duration)
  }

  return { visible, message, type, show }
}
