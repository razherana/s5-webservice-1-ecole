import type { Semestre } from '@/types'
import { etudiantsService, semestreService } from '.'

export type SemestreAverage = {
  semestre: Semestre
  notes: number[]
  moyenne: number
}

type CycleKey = 'ALL' | 'L1' | 'L2'

const CYCLE_SEMESTER_MAP: Record<CycleKey, string[]> = {
  ALL: ['Semestre 1', 'Semestre 2', 'Semestre 3', 'Semestre 4'],
  L1: ['Semestre 1', 'Semestre 2'],
  L2: ['Semestre 3', 'Semestre 4']
}

class SemestreCalculsService {
  private semestreMoyennesCache = new Map<number, SemestreAverage[]>()
  private allSemestres: Semestre[] = []

  extractNoteValue(value: unknown): number | null {
    if (typeof value === 'number') {
      return Number.isFinite(value) ? value : null
    }
    if (typeof value === 'string' && value.trim() !== '') {
      const parsed = Number(value)
      return Number.isFinite(parsed) ? parsed : null
    }
    return null
  }

  calculateAverage(values: number[]): number {
    if (values.length === 0) {
      return 0
    }
    const total = values.reduce((sum, current) => sum + current, 0)
    return total / values.length
  }

  private async ensureSemestresLoaded(): Promise<void> {
    if (this.allSemestres.length > 0) {
      return
    }
    try {
      this.allSemestres = await semestreService.fetchSemestres()
    } catch (err: unknown) {
      console.error('Erreur lors du chargement des semestres:', err)
      throw err
    }
  }

  async loadSemestreMoyennes(etudiantId: number): Promise<SemestreAverage[]> {
    if (this.semestreMoyennesCache.has(etudiantId)) {
      const cached = this.semestreMoyennesCache.get(etudiantId)!
      return cached.map((item) => ({
        semestre: item.semestre,
        notes: [...item.notes],
        moyenne: item.moyenne
      }))
    }

    try {
      await this.ensureSemestresLoaded()
      const targetLabels = CYCLE_SEMESTER_MAP.ALL
      const targetSemestres = targetLabels
        .map((label) =>
          this.allSemestres.find((sem) => sem.libelle.toLowerCase() === label.toLowerCase())
        )
        .filter((semestre): semestre is Semestre => Boolean(semestre))

      const averages = await Promise.all(
        targetSemestres.map(async (semestre) => {
          try {
            const notes = await etudiantsService.fetchNotesBySemestreAndEtudiant(
              semestre.id,
              etudiantId
            )
            const valeurs = notes
              .map((note) => this.extractNoteValue(note.note ?? note.valeur))
              .filter((note): note is number => note !== null)

            return {
              semestre,
              notes: valeurs,
              moyenne: Number(this.calculateAverage(valeurs).toFixed(2))
            } satisfies SemestreAverage
          } catch (err: unknown) {
            console.error(`Erreur lors du chargement des notes pour le semestre ${semestre.libelle}:`, err)
            return {
              semestre,
              notes: [],
              moyenne: 0
            } satisfies SemestreAverage
          }
        })
      )

      averages.sort((a, b) =>
        a.semestre.libelle.localeCompare(b.semestre.libelle, 'fr', { numeric: true })
      )

      this.semestreMoyennesCache.set(etudiantId, averages)
      return averages
    } catch (err: unknown) {
      console.error('Erreur lors du calcul des moyennes par semestre:', err)
      throw err
    }
  }

  getCycleSemesterMap(): Record<CycleKey, string[]> {
    return CYCLE_SEMESTER_MAP
  }

  getCycleOptions(): Array<{ key: CycleKey; label: string }> {
    return [
      { key: 'ALL', label: 'Tous (S1 - S4)' },
      { key: 'L1', label: 'L1 (S1 + S2)' },
      { key: 'L2', label: 'L2 (S3 + S4)' }
    ]
  }

  getMoyenneColor(moyenne: number | undefined): string {
    if (!moyenne) return 'text-gray-600'
    if (moyenne >= 16) return 'text-green-600'
    if (moyenne >= 14) return 'text-blue-600'
    if (moyenne >= 12) return 'text-yellow-600'
    return 'text-red-600'
  }

  getNoteColor(note: number): string {
    if (note >= 16) return 'text-green-600'
    if (note >= 14) return 'text-blue-600'
    if (note >= 12) return 'text-yellow-600'
    return 'text-red-600'
  }

  clearCache(): void {
    this.semestreMoyennesCache.clear()
  }
}

export default new SemestreCalculsService()
