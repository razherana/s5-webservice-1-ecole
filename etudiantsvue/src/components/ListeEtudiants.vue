<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { etudiantsService } from '@/services'
import type { EtudiantAvecMoyenne } from '@/types'

interface Props {
  semestreId: number | null
}

const props = withDefaults(defineProps<Props>(), {
  semestreId: null
})

const etudiants = ref<EtudiantAvecMoyenne[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

const loadEtudiants = async () => {
  if (!props.semestreId) {
    etudiants.value = []
    return
  }

  loading.value = true
  error.value = null
  try {
    etudiants.value = await etudiantsService.fetchEtudiantsAvecMoyennes(props.semestreId)
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des étudiants'
    error.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    loading.value = false
  }
}

const getMoyenneColor = (moyenne: number | undefined) => {
  if (!moyenne) return 'text-gray-600'
  if (moyenne >= 16) return 'text-green-600'
  if (moyenne >= 14) return 'text-blue-600'
  if (moyenne >= 12) return 'text-yellow-600'
  return 'text-red-600'
}

watch(() => props.semestreId, () => {
  loadEtudiants()
})

onMounted(() => {
  loadEtudiants()
})
</script>

<template>
  <div class="bg-white rounded-lg shadow mt-6">
    <div class="p-6">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">Étudiants et Moyennes</h2>

      <div v-if="!semestreId" class="text-center py-8">
        <p class="text-gray-500">Sélectionnez un semestre pour voir les étudiants</p>
      </div>

      <div v-else>
        <div v-if="loading" class="text-center py-8">
          <p class="text-gray-600">Chargement des étudiants...</p>
        </div>

        <div v-if="error" class="text-red-600 bg-red-100 p-4 rounded mb-4">
          {{ error }}
        </div>

        <div v-if="!loading && etudiants.length === 0" class="text-center py-8">
          <p class="text-gray-500">Aucun étudiant disponible</p>
        </div>

        <div v-if="!loading && etudiants.length > 0" class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="border-b-2 border-gray-300">
                <th class="px-4 py-3 text-left text-gray-700 font-semibold">Nom</th>
                <th class="px-4 py-3 text-left text-gray-700 font-semibold">Prénom</th>
                <th class="px-4 py-3 text-right text-gray-700 font-semibold">Moyenne</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="etudiant in etudiants" :key="etudiant.id"
                class="border-b border-gray-200 hover:bg-gray-50 transition-colors">
                <td class="px-4 py-3 text-gray-800">{{ etudiant.nom }}</td>
                <td class="px-4 py-3 text-gray-800">{{ etudiant.prenom }}</td>
                <td class="px-4 py-3 text-right font-bold" :class="getMoyenneColor(etudiant.moyenne)">
                  {{ etudiant.moyenne !== undefined ? etudiant.moyenne.toFixed(2) : '-' }} / 20
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
