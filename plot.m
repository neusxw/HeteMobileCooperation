clc;
clear all;
close all;
a=load('D:\GitHub\Reast\HeteMobileCooperation\repastOutput.txt');
figure;
plot(a(:,1),a(:,2)/100,'-b');
legend('������')
figure;
plot(a(:,1),a(:,3),'-g',a(:,1),a(:,4),'-r',a(:,1),a(:,5),'-y');
legend('ƽ���ƶ�����','������ƽ���ƶ�����','������ƽ���ƶ�����')
figure;
plot(a(:,1),a(:,6),'-b',a(:,1),a(:,7),'-r');
legend('������ƽ��֧��','������ƽ��֧��')
figure;
plot(a(:,1),a(:,8),'-b',a(:,1),a(:,9),'-r');
legend('D2C','C2D')