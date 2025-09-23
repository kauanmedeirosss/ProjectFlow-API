// Script para Auto-import
const fs = require('fs');
const path = require('path');

const postmanFolder = './postman';

// Lista todos os arquivos .json da pasta postman
const files = fs.readdirSync(postmanFolder)
  .filter(file => file.endsWith('.json'))
  .map(file => path.join(postmanFolder, file));

console.log('📁 Arquivos Postman encontrados:');
files.forEach(file => console.log(`- ${file}`));

console.log('\n📋 Para importar manualmente:');
console.log('1. Abra o Postman');
console.log('2. Clique em Import');
console.log('3. Selecione os arquivos acima');