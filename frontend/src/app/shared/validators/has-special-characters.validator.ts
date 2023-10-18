export function hasSpecialCharacters(text:string) {
    const regex = /[^a-zA-Z0-9\sÀ-ỹ]/g;
    return regex.test(text);
}