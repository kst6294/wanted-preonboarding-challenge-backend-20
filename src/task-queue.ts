export default class TaskQueue {
  private concurrency: number;
  private running: number = 0;
  private queue: (() => Promise<void>)[] = [];

  constructor(concurrency: number) {
    this.concurrency = concurrency;
  }

  runTask<T>(task: () => Promise<T>): Promise<T> {
    return new Promise((resolve, reject) => {
      this.queue.push(async () => {
        try {
          resolve(await task());
        } catch (err) {
          reject(err);
        }
      });

      process.nextTick(() => this.next());
    });
  }

  private async next(): Promise<void> {
    while (this.running < this.concurrency && this.queue.length) {
      const task = this.queue.shift();

      if (!task) return;

      this.running += 1;

      try {
        await task();
      } finally {
        this.running -= 1;
        this.next();
      }
    }
  }
}
