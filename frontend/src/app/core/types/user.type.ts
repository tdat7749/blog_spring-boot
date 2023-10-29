export interface User {
  email: string | unknown,
  firstName: string | unknown,
  lastName: string | unknown,
  userName:string | unknown,
  role: string | unknown,
  avatar: string | unknown,
  id: number | unknown
  notLocked: boolean | unknown
  summary: string | unknown
}

export interface UserAbs{
  firstName: string | unknown,
  lastName: string | unknown,
  userName:string | unknown,
  id: number | unknown,
  avatar: string | unknown,
}


export interface ForgotPassword {
  newPassword:string,
  confirmPassword:string,
  code:string,
  email:string
}

export interface ChangePassword {
  oldPassword:string,
  newPassword:string,
  confirmPassword:string
}

export interface ChangeInformation {
  firstName:string
  lastName:string
  summary: string
}

export type Role = "ADMIN" | "USER"

export interface ChangePermission {
  userId:number,
  role:Role
}