function plotsegments2d

load c:\CUTS\data\plotsegmentsX.dat;
load c:\CUTS\data\plotsegmentsY.dat;
load c:\CUTS\data\plotsegmentsZ.dat;
x = plotsegmentsX;
y = plotsegmentsY;
z = plotsegmentsZ;
%plot3(x,y,z,'g');
plot3(z,x,y,'m');
grid;
title('CURVE SEGMENTS');
xlabel('Similarity z');
ylabel('Time');
zlabel('Similarity y');