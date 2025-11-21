<script setup lang="ts">
import { computed } from 'vue'
import { semestreCalculsService } from '@/services'
import type { EtudiantAvecMoyenne, StudentOptionDetail } from '@/types'
import type { SemestreAverage } from '@/services/semestreCalculsService'

interface Props {
  open: boolean
  selectedEtudiant: EtudiantAvecMoyenne | null
  optionsDetails: StudentOptionDetail[]
  optionsLoading: boolean
  optionsError: string | null
  semestreMoyennes: SemestreAverage[]
  semestreMoyennesLoading: boolean
  semestreMoyennesError: string | null
  currentCycle: 'ALL' | 'L1' | 'L2'
}

const props = withDefaults(defineProps<Props>(), {
  open: false,
  selectedEtudiant: null,
  optionsLoading: false,
  semestreMoyennesLoading: false
})

const emit = defineEmits<{
  close: []
  cycleChanage: [cycle: 'ALL' | 'L1' | 'L2']
}>()

const CYCLE_SEMESTER_MAP = semestreCalculsService.getCycleSemesterMap()
const cycleOptions = semestreCalculsService.getCycleOptions()

const filteredSemestreMoyennes = computed(() => {
  const targetLabels = CYCLE_SEMESTER_MAP[props.currentCycle]
  if (props.currentCycle === 'ALL') {
    return props.semestreMoyennes
  }
  return props.semestreMoyennes.filter((item) => targetLabels.includes(item.semestre.libelle))
})

const aggregatedCycleAverage = computed(() => {
  if (filteredSemestreMoyennes.value.length === 0) {
    return null
  }

  const totalNotes = filteredSemestreMoyennes.value.reduce((sum, item) => sum + item.notes.length, 0)
  if (totalNotes === 0) {
    const simpleAverage = filteredSemestreMoyennes.value.reduce((sum, item) => sum + item.moyenne, 0)
    return Number((simpleAverage / filteredSemestreMoyennes.value.length).toFixed(2))
  }

  const totalSum = filteredSemestreMoyennes.value.reduce((sum, item) => {
    return sum + item.notes.reduce((noteSum, value) => noteSum + value, 0)
  }, 0)

  return Number((totalSum / totalNotes).toFixed(2))
})

const aggregatedCycleNotes = computed(() => {
  return filteredSemestreMoyennes.value.flatMap((item) => item.notes)
})

const aggregatedCycleLabel = computed(() => {
  if (props.currentCycle === 'L1') {
    return 'Moyenne L1 (S1 + S2)'
  }
  if (props.currentCycle === 'L2') {
    return 'Moyenne L2 (S3 + S4)'
  }
  return 'Moyenne générale (S1 - S4)'
})

const handleClose = () => {
  emit('close')
}

const handleCycleChange = (cycle: 'ALL' | 'L1' | 'L2') => {
  emit('cycleChanage', cycle)
}

const getMoyenneColor = (moyenne: number | undefined) => {
  return semestreCalculsService.getMoyenneColor(moyenne)
}

const getNoteColor = (note: number) => {
  return semestreCalculsService.getNoteColor(note)
}
</script>

<template>
  <!-- Modal -->
  <div v-if="open" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-lg shadow-xl max-w-4xl w-full max-h-[90vh] overflow-y-auto">
      <!-- Modal Header -->
      <div class="sticky top-0 bg-linear-to-r from-blue-600 to-blue-700 text-white p-6 border-b">
        <div class="flex justify-between items-start">
          <div>
            <h3 class="text-2xl font-bold">
              {{ selectedEtudiant?.prenom }} {{ selectedEtudiant?.nom }}
            </h3>
            <p class="text-blue-100 mt-1">Détails des options et unités</p>
          </div>
          <button @click="handleClose" class="text-white hover:text-gray-200 transition-colors">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <!-- Modal Body -->
      <div class="p-6">
        <div v-if="selectedEtudiant" class="mb-6 grid gap-4 sm:grid-cols-2">
          <div class="bg-blue-50 border border-blue-100 rounded-lg p-4">
            <p class="text-sm font-semibold uppercase tracking-wide text-blue-600">Identité</p>
            <p class="mt-2 text-lg font-medium text-gray-800">
              {{ selectedEtudiant.prenom }} {{ selectedEtudiant.nom }}
            </p>
            <p class="mt-1 text-sm text-gray-500">ID étudiant : {{ selectedEtudiant.id }}</p>
          </div>

          <div class="bg-blue-50 border border-blue-100 rounded-lg p-4">
            <p class="text-sm font-semibold uppercase tracking-wide text-blue-600">Moyenne actuelle</p>
            <p class="mt-2 text-2xl font-bold" :class="getMoyenneColor(selectedEtudiant.moyenne)">
              {{ selectedEtudiant.moyenne !== undefined ? selectedEtudiant.moyenne.toFixed(2) : '-' }} / 20
            </p>
            <p class="mt-1 text-sm text-gray-500">Basée sur le semestre sélectionné</p>
          </div>
        </div>

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
                  <tr v-for="unite in option.unites" :key="unite.uniteId" class="border-b border-gray-200">
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

        <div class="mt-8 border-t border-gray-200 pt-6">
          <div class="mb-4 flex flex-wrap items-center justify-between gap-2">
            <h4 class="text-lg font-semibold text-gray-800">Moyennes par semestre</h4>
            <div class="flex flex-wrap gap-2">
              <button v-for="cycle in cycleOptions" :key="cycle.key" type="button"
                @click="handleCycleChange(cycle.key)"
                :class="[
                  'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                  currentCycle === cycle.key
                    ? 'bg-blue-600 text-white shadow'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                ]">
                {{ cycle.label }}
              </button>
            </div>
          </div>

          <div v-if="semestreMoyennesLoading" class="py-6 text-center text-gray-600">
            Calcul des moyennes par semestre...
          </div>

          <div v-else-if="semestreMoyennesError" class="rounded border border-red-200 bg-red-50 p-4 text-red-700">
            {{ semestreMoyennesError }}
          </div>

          <div v-else-if="filteredSemestreMoyennes.length === 0" class="py-6 text-center text-gray-500">
            Aucune moyenne disponible pour cet étudiant.
          </div>

          <div v-else class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200 text-sm">
              <thead class="bg-gray-100">
                <tr>
                  <th class="px-4 py-2 text-left font-semibold text-gray-700">Semestre</th>
                  <th class="px-4 py-2 text-left font-semibold text-gray-700">Année</th>
                  <th class="px-4 py-2 text-right font-semibold text-gray-700">Moyenne</th>
                  <th class="px-4 py-2 text-left font-semibold text-gray-700">Notes</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in filteredSemestreMoyennes" :key="item.semestre.id" class="border-b border-gray-200">
                  <td class="px-4 py-2 text-gray-800">{{ item.semestre.libelle }}</td>
                  <td class="px-4 py-2 text-gray-600">{{ item.semestre.annee }}</td>
                  <td class="px-4 py-2 text-right font-bold" :class="getMoyenneColor(item.moyenne)">
                    {{ item.moyenne.toFixed(2) }} / 20
                  </td>
                  <td class="px-4 py-2 text-gray-700">
                    <span v-if="item.notes.length === 0" class="text-gray-400">Aucune note</span>
                    <span v-else>{{ item.notes.map((note) => note.toFixed(2)).join(' • ') }}</span>
                  </td>
                </tr>
              </tbody>
              <tfoot v-if="aggregatedCycleAverage !== null">
                <tr class="bg-blue-50 font-medium text-blue-900">
                  <td class="px-4 py-2" colspan="2">{{ aggregatedCycleLabel }}</td>
                  <td class="px-4 py-2 text-right" :class="getMoyenneColor(aggregatedCycleAverage)">
                    {{ aggregatedCycleAverage.toFixed(2) }} / 20
                  </td>
                  <td class="px-4 py-2">
                    <span v-if="aggregatedCycleNotes.length === 0" class="text-blue-300">Aucune note</span>
                    <span v-else>{{ aggregatedCycleNotes.map((note) => note.toFixed(2)).join(' • ') }}</span>
                  </td>
                </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </div>

      <!-- Modal Footer -->
      <div class="sticky bottom-0 bg-gray-100 border-t p-4 flex justify-end gap-3">
        <button @click="handleClose"
          class="px-4 py-2 bg-gray-300 text-gray-800 rounded-lg hover:bg-gray-400 transition-colors font-medium">
          Fermer
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
