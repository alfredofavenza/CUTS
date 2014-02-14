function plotsegments

load c:\CUTS\data\plotsegmentsX.dat;
load c:\CUTS\data\plotsegmentsY.dat;
x = plotsegmentsX;
y = plotsegmentsY;
plot(x,y);
grid;
title('CURVE SEGMENTS');
xlabel('Time');
ylabel('Similarity');
