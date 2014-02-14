function plotbts

load c:\CUTS\data\plotbtsX.dat;
load c:\CUTS\data\plotbtsY.dat;
x = plotbtsX;
y = plotbtsY;
plot(x,y);
grid;
title('BASE TOPIC SEGMENTS');
xlabel('Time');
zlabel('Similarity');