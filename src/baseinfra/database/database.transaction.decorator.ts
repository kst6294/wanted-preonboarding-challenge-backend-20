import { DataSource } from 'typeorm';

export const Transactional1 = async (dataSource: DataSource, callback: (...args: any[]) => Promise<any>) => {
  const queryRunner = dataSource.createQueryRunner();
  await queryRunner.connect();
  await queryRunner.startTransaction();
  try {
    const result = await callback();
    await queryRunner.commitTransaction();
    return result;
  } catch (err) {
    await queryRunner.rollbackTransaction();
  } finally {
    await queryRunner.release();
  }
};


export function TranscationalBefore(dataSource?: DataSource) {
  if (!dataSource) {
    console.log("datasource is null");
    return;
  }
  return function (target: any, propertyName: string, descriptor: TypedPropertyDescriptor<any>) {
    const originalMethod = descriptor.value;

    descriptor.value = async function (...args: any[]) {

      const queryRunner = dataSource.createQueryRunner();
      await queryRunner.connect();
      await queryRunner.startTransaction();

      try {
        const result = await originalMethod.apply(this, [queryRunner, ...args]);
        await queryRunner.commitTransaction();
        console.log("result", result);
        return result;
      } catch (err) {
        await queryRunner.rollbackTransaction();
      } finally {
        await queryRunner.release();
      }
      return descriptor;
    };
  };
}