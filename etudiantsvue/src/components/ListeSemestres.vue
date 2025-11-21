<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { semestreService } from '@/services'
import type { Semestre, Option } from '@/types'

const semestres = ref<Semestre[]>([])
const options = ref<Option[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

const emit = defineEmits<{
  selectSemestre: [semestre: Semestre]
}>()

const loadSemestres = async () => {
  loading.value = true
  error.value = null
  try {
    semestres.value = await semestreService.fetchSemestres()
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des semestres'
    error.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  try {
    options.value = await semestreService.fetchOptions()
  } catch (err: unknown) {
    console.error('Erreur lors du chargement des options:', err)
  }
}

const selectSemestre = (semestre: Semestre) => {
  emit('selectSemestre', semestre)
}

onMounted(() => {
  loadSemestres()
  loadOptions()
})
</script>

<template>
  <div class="bg-white rounded-lg shadow">
    <div class="p-6">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">Semestres et Options</h2>

      <div v-if="loading" class="text-center py-8">
        <p class="text-gray-600">Chargement des semestres et options...</p>
      </div>

      <div v-if="error" class="text-red-600 bg-red-100 p-4 rounded mb-4">
        {{ error }}
      </div>

      <div v-if="!loading && semestres.length === 0" class="text-center py-8">
        <p class="text-gray-500">Aucun semestre disponible</p>
      </div>

      <div v-if="!loading && semestres.length > 0" class="space-y-6">
        <div v-for="semestre in semestres" :key="semestre.id" class="border-l-4 border-blue-500 pl-4">
          <h3 class="text-lg font-semibold text-gray-800 mb-4">{{ semestre.annee }} - {{ semestre.libelle }}</h3>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <button
              v-for="option in options.filter(o => o.semestreId === semestre.id)"
              :key="option.id"
              @click="selectSemestre(semestre)"
              class="p-6 bg-linear-to-br from-blue-50 to-blue-100 hover:from-blue-100 hover:to-blue-200 rounded-lg border-2 border-blue-300 transition-all duration-200 transform hover:scale-105 cursor-pointer text-left">
              <div class="flex flex-col">
                <span class="font-bold text-blue-600">{{ option.code }}</span>
                <span class="text-sm text-gray-700 mt-2">{{ option.libelle }}</span>
              </div>
            </button>
          </div>

          <div v-if="options.filter(o => o.semestreId === semestre.id).length === 0" class="text-center py-4">
            <p class="text-gray-500">Aucune option disponible pour ce semestre</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
