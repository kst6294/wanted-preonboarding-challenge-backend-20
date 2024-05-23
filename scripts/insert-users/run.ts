import InsertUsers from ".";
import database from "../../src/database";

(async function () {
  try {
    const { affectedRows } = await InsertUsers.run();

    console.log(`users 테이블에 ${affectedRows} 개의 레코드가 추가되었습니다.`);
  } catch (error) {
    console.error(error);
  } finally {
    await database.pool.end();
  }
})();
