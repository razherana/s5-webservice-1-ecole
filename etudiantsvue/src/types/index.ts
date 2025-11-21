export interface Semestre {
  id: number
  libelle: string
  annee: string
}

export interface APIRespone<S> {
  status: 'success' | 'error'
  data: S | null
  error: {
    code: number
    message: string
    details?: { [key: string]: string }
  } | null
}

export interface LoginForm {
  username: string
  password: string
}
