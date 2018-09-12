import hashlib
import sys
def getHashcode(string):
    for i in range(10000000000):
        hash_md5 = hashlib.md5(str(i).encode('utf-8'))
        res = hash_md5.hexdigest()
        if res[0:len(string)] == string:
            print(i)
            exit()


if __name__ == '__main__':
    getHashcode(sys.argv[1])