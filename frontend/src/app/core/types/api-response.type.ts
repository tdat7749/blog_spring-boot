export interface ApiResponse<T>{
  data: T,
  message: string,
  httpCode?: number
}
