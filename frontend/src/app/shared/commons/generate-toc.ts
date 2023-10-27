import { TOC } from "src/app/core/types/post.type"

export function generateTOC(content: string): TOC[] {
    const parser = new DOMParser()
    const doc = parser.parseFromString(content, "text/html")

    const headings = doc.body.querySelectorAll("h1,h2,h3")
    let result: TOC[] = []

    headings.forEach((item) => {
        result.push({
            tag: item.tagName,
            id: item.getAttribute("id") || "",
            title: item.textContent as string
        })
    })

    return result
}