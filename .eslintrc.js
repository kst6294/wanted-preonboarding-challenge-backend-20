module.exports = {
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: 'tsconfig.json',
    tsconfigRootDir: __dirname,
    sourceType: 'module',
  },
  plugins: ['@typescript-eslint'],
  extends: [
    'airbnb-typescript/base',
    'plugin:jest/recommended',
    'plugin:jest/style',
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended',
    'plugin:import/recommended',
  ],
  root: true,
  env: {
    node: true,
    jest: true,
    'jest/globals': true,
  },
  ignorePatterns: ['.eslintrc.js', '**/node_modules/**', 'dist/**'],
  rules: {
    '@typescript-eslint/interface-name-prefix': 'off',
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/explicit-module-boundary-types': 'off',
    '@typescript-eslint/no-explicit-any': 'off',
    'prettier/prettier': 'error',
    semi: ['error', 'always'],
    'import/no-unresolved': 0,
    'no-console': 0,
    'import/prefer-default-export': 0,
    'consistent-return': 0,
    'no-confusing-arrow': 0,
    'arrow-body-style': 0,
    'max-len': 0,
    'no-extra-boolean-cast': 0,
    'no-nested-ternary': 0,
    'func-names': 0,
    'prefer-const': 0,
    'no-underscore-dangle': 0,
    'no-useless-return': 0,
    'no-async-promise-executor': 0,
    '@typescript-eslint/no-unused-vars': ['error'],
    '@typescript-eslint/naming-convention': [
      'error',
      {
        selector: 'default',
        format: ['camelCase'],
      }, // 변수는 기본적으로 CamelCase으로 작성.
      {
        selector: 'variable',
        format: ['camelCase', 'UPPER_CASE', 'PascalCase'], // 변수는 camelCase, UPPER_CASE, StrictPascalCase 중 선택가능.
        leadingUnderscore: 'allow',
      },
      {
        selector: 'parameter', // 매개변수는 camelCase, 선행 underscore를 허용한다.
        format: ['camelCase'],
        leadingUnderscore: 'allow',
      },
      {
        selector: 'typeLike',
        format: ['PascalCase'],
      }, // 클래스,인터페이스,타입 등은 대문자로 시작한다.
      {
        selector: 'enumMember',
        format: ['UPPER_CASE'],
      }, // enum 타입 키값은 대문자를 사용한다.
    ],
  },
};
