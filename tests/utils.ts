export default function getUserID(cookie: string) {
  const match = cookie.match(/userID=(\d+);/);
  return match ? Number(match[1]) : null;
}
