/**
 * Prepends the base URL to the given endpoint.
 * @param url Must start with /
 * @returns
 */
export default function api(url: string) {
  return `http://localhost:8080${url}`
}
