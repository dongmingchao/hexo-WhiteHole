#!node

const crypto = require('crypto');
// console.log(md5.update('0').digest('hex'));
for (let i = 0; i < 10000000000; i++) {
    let md5 = crypto.createHash('md5');
    let af = md5.update(i.toString()).digest('hex');
    if (af.slice(0, process.argv[2].length) === process.argv[2]) {
        console.log(i);
        return;
    }
}