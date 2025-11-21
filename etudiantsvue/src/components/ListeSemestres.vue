<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/utils/axiosInstance'
import api from '@/utils/api'
import type { APIRespone, Semestre } from '@/types'

const semestres = ref<Semestre[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

const emit = defineEmits<{
  selectSemestre: [semestre: Semestre]
}>()

const fetchSemestres = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await axiosInstance.get(api('/api/semestres'))

    const responseData = response.data as APIRespone<{
      semestres: Semestre[]
    }>

    console.log('Données des semestres reçues:', responseData)

    semestres.value = responseData.data?.semestres || []
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des semestres'
    error.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    loading.value = false
  }
}

const selectSemestre = (semestre: Semestre) => {
  emit('selectSemestre', semestre)
}

onMounted(() => {
  fetchSemestres()
})
</script>

<template>
  <div class="bg-white rounded-lg shadow">
    <div class="p-6">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">Semestres</h2>

      <div v-if="loading" class="text-center py-8">
        <p class="text-gray-600">Chargement des semestres...</p>
      </div>

      <div v-if="error" class="text-red-600 bg-red-100 p-4 rounded mb-4">
        {{ error }}
      </div>

      <div v-if="!loading && semestres.length === 0" class="text-center py-8">
        <p class="text-gray-500">Aucun semestre disponible</p>
      </div>

      <div v-if="!loading && semestres.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <button v-for="semestre in semestres" :key="semestre.id" @click="selectSemestre(semestre)"
          class="p-6 bg-linear-to-br from-blue-50 to-blue-100 hover:from-blue-100 hover:to-blue-200 rounded-lg border-2 border-blue-300 transition-all duration-200 transform hover:scale-105 cursor-pointer text-left">
          <div class="flex flex-col items-center">
            <span class="font-bold text-blue-600">{{ semestre.annee }}</span>
            <span class="text-sm text-gray-700 mt-2">{{ semestre.libelle }}</span>
          </div>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
