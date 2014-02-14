function mymds1d

warning off MATLAB:colon:operandsNotRealScalar;
load c:\CUTS\data\vettoriTFIDF.dat;
load c:\CUTS\data\timepoints.dat;
D = pdist(vettoriTFIDF,'euclidean');
[Y,e] = cmdscale(D);
T = timepoints;
sz = size(Y(:,1));
xvett = 0:1:sz-1;
%plot3(xvett,Y(:,1),Y(:,2),'r');
%plot3(Y(:,2), xvett,Y(:,1),'b');
plot3(Y(:,2), T, Y(:,1), 'b');
grid;
title('2D MULTI DIMENSIONAL SCALING');
xlabel('Similarity z');
ylabel('Time');
zlabel('Similarity y');

file = fopen('c:\CUTS\data\mdsres.dat','w+');
for i=1:sz
count = fprintf(file, '%f ',Y(i,1:2));
end
fclose(file);