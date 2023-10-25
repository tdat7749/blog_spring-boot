export interface Notification{
    id:number,
    link:string,
    message:string,
    read:boolean
}

export interface RpNotification{
    unSeenNotification:number,
    notifications:Notification[]
}