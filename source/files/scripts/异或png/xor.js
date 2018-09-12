#!node

const fs = require('fs');
let out = fs.createWriteStream('xor.png');
let input = fs.createReadStream(process.argv[2]);
input.on('data', data => {
    let chunk = [];
    for (let each of data) chunk.push(each ^ 0xff);
    out.write(Buffer.from(chunk));
});
console.log(process.argv[2] + ' finish');