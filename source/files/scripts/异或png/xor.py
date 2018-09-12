#!python
# coding=utf-8
import sys

def xor(filename):
    with open(filename, 'rb') as f, open('xor.png', 'wb') as wf:
        for each in f.read():
            wf.write(each ^ 0xff)


if __name__ == '__main__':
    xor(sys.argv[1])