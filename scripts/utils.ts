export function makeIDs(size: number) {
  return Array.from({ length: size }, (_, index) => index + 1);
}

export function makeIDIterator(array: number[]) {
  let index = 0;

  return () => {
    const id = array[index];
    index = (index + 1) % array.length;

    return id;
  };
}

export function getRandomStatus<T extends Record<string, unknown>>(
  TStatus: T
): T[keyof T] {
  const values = Object.values(TStatus) as T[keyof T][];
  const idx = Math.floor(Math.random() * values.length);

  return values[idx];
}

export function makeRandomIDMaker(size: number) {
  const IDs = makeIDs(size);

  return () => {
    const idx = Math.floor(Math.random() * size);

    return IDs[idx];
  };
}
