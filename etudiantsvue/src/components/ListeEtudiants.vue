<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { etudiantsService } from '@/services'
import type { EtudiantAvecMoyenne, StudentOptionDetail } from '@/types'

interface Props {
  semestreId: number | null
}

const props = withDefaults(defineProps<Props>(), {
  semestreId: null
})

const etudiants = ref<EtudiantAvecMoyenne[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const selectedEtudiant = ref<EtudiantAvecMoyenne | null>(null)
const modalOpen = ref(false)
const optionsDetails = ref<StudentOptionDetail[]>([])
const optionsLoading = ref(false)
const optionsError = ref<string | null>(null)

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

const openModal = async (etudiant: EtudiantAvecMoyenne) => {
  selectedEtudiant.value = etudiant
  modalOpen.value = true
  optionsLoading.value = true
  optionsError.value = null

  try {
    if (props.semestreId) {
      optionsDetails.value = await etudiantsService.fetchStudentOptionsDetails(props.semestreId, etudiant.id)
    }
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des options'
    optionsError.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    optionsLoading.value = false
  }
}

const closeModal = () => {
  modalOpen.value = false
  selectedEtudiant.value = null
  optionsDetails.value = []
}

const getNoteColor = (note: number) => {
  if (note >= 16) return 'text-green-600'
  if (note >= 14) return 'text-blue-600'
  if (note >= 12) return 'text-yellow-600'
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
                @click="openModal(etudiant)"
                class="border-b border-gray-200 hover:bg-blue-50 transition-colors cursor-pointer">
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

  <!-- Modal -->
  <div v-if="modalOpen" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-lg shadow-xl max-w-2xl w-full max-h-96 overflow-y-auto">
      <!-- Modal Header -->
      <div class="sticky top-0 bg-linear-to-r from-blue-600 to-blue-700 text-white p-6 border-b">
        <div class="flex justify-between items-start">
          <div>
            <h3 class="text-2xl font-bold">
              {{ selectedEtudiant?.prenom }} {{ selectedEtudiant?.nom }}
            </h3>
            <p class="text-blue-100 mt-1">Détails des options et unités</p>
          </div>
          <button
            @click="closeModal"
            class="text-white hover:text-gray-200 transition-colors"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <!-- Modal Body -->
      <div class="p-6">
        <div v-if="optionsLoading" class="text-center py-8">
          <p class="text-gray-600">Chargement des options...</p>
        </div>

        <div v-if="optionsError" class="text-red-600 bg-red-100 p-4 rounded mb-4">
          {{ optionsError }}
        </div>

        <div v-if="!optionsLoading && optionsDetails.length === 0" class="text-center py-8">
          <p class="text-gray-500">Aucune option disponible</p>
        </div>

        <div v-if="!optionsLoading && optionsDetails.length > 0" class="space-y-6">
          <div v-for="option in optionsDetails" :key="option.optionId" class="border rounded-lg p-4 bg-gray-50">
            <h4 class="text-lg font-semibold text-gray-800 mb-4">{{ option.optionLibelle }}</h4>

            <div class="overflow-x-auto">
              <table class="w-full text-sm">
                <thead>
                  <tr class="border-b-2 border-gray-300 bg-gray-100">
                    <th class="px-3 py-2 text-left text-gray-700 font-semibold">Code</th>
                    <th class="px-3 py-2 text-left text-gray-700 font-semibold">Intitulé</th>
                    <th class="px-3 py-2 text-left text-gray-700 font-semibold">Groupe</th>
                    <th class="px-3 py-2 text-center text-gray-700 font-semibold">Crédits</th>
                    <th class="px-3 py-2 text-center text-gray-700 font-semibold">Note</th>
                    <th class="px-3 py-2 text-left text-gray-700 font-semibold">Session</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="unite in option.unites" :key="unite.uniteId"
                    class="border-b border-gray-200 hover:bg-white transition-colors">
                    <td class="px-3 py-2 text-gray-800 font-mono text-xs">{{ unite.codeMatiere }}</td>
                    <td class="px-3 py-2 text-gray-800">{{ unite.intitule }}</td>
                    <td class="px-3 py-2 text-gray-700 text-xs">{{ unite.groupeLibelle }}</td>
                    <td class="px-3 py-2 text-center text-gray-700">{{ unite.credits }}</td>
                    <td class="px-3 py-2 text-center font-bold" :class="getNoteColor(unite.note)">
                      {{ unite.note.toFixed(2) }}
                    </td>
                    <td class="px-3 py-2 text-gray-700 text-xs">{{ unite.sessionLibelle }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Modal Footer -->
      <div class="sticky bottom-0 bg-gray-100 border-t p-4 flex justify-end gap-3">
        <button
          @click="closeModal"
          class="px-4 py-2 bg-gray-300 text-gray-800 rounded-lg hover:bg-gray-400 transition-colors font-medium"
        >
          Fermer
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
