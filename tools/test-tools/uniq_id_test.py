#!/usr/bin/env python
#encoding=utf8
'''
'''
import urllib2
import sys
import random
import multiprocessing
import time
from threading import Thread

DEFAULT_REQUEST_COUNT = 100000

def run(thread_id, request_count, result_lst):
    ''''''
    for i in xrange(request_count):
        url = 'http://127.0.0.1:8080/id/%s' % (random.randint(0, 10000))
        rst = urllib2.urlopen(url).read()

        data = json.loads(rst)
        result_lst[thread_id].add((data['namespace'], data['seq']))

def main(request_count):
    ''''''
    threads_cnt = multiprocessing.cpu_count()
    result_lst = [set() for n in threads_cnt]

    threads = []
    for i in xrange(threads_cnt):
        t = Thread(target=run, args=(i, request_count, result_lst))
        threads.append(t)

    for t in threads:
        t.start()

    while True:
        time.sleep(1)

if __name__ == '__main__':

    main(int(sys.argv[1]))
