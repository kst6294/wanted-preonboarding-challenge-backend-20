import dbClient from "../db/dbClient.js";

function dao_wrapper(table_name) {
    const table = () => dbClient(table_name);
    const record_name = table_name.toLowerCase().slice(0, -1);

    return {
        [`get_all_${table_name.toLowerCase()}`]: () => table(),

        [`get_${record_name}`]: (dto) => table().where(dto).first(),

        [`get_${record_name}s`]: (dto) => table().where(dto),

        [`insert_${record_name}`]: (dto) => table().insert(dto),

        [`update_${record_name}`]: (dto, changed) => table().where(dto).update(changed),
    };
}

export default dao_wrapper;
