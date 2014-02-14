function plotbts2d

load c:\CUTS\data\plotbtsX.dat;
load c:\CUTS\data\plotbtsY.dat;
load c:\CUTS\data\plotbtsZ.dat;
x = plotbtsX;
y = plotbtsY;
z = plotbtsZ;
%plot3(x,y,z,'r');
plot3(z,x,y,'c');
grid;
title('BASE TOPIC SEGMENTS');
xlabel('Similarity z');
ylabel('Time');
zlabel('Similarity y');