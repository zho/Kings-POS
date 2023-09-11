#!/bin/sh

mysqldump --column-statistics=0 -uroot -pkoperasiroot123!! -h 180.211.92.2 -P 3333 --routines koperasipos > koperasipos.sql