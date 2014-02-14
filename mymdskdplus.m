function mymdskdplus

load c:\CUTS\data\vettoriTFIDF.dat;
load c:\CUTS\data\timepoints.dat;
%load vettoriTFIDF.dat;
D = pdist(vettoriTFIDF,'euclidean');
T = timepoints;
%squareform(D);
[Y,e] = cmdscale(D);
s=sum(e)
sz=size(e)
acc=0
for i=1:sz(1)
    acc=acc+e(i);
    if acc/s > 0.50
        break;
    end
end
S= Y(:,1:i);
P = S(:,1:i)
PP = abs(sum(P')')
plot(T,PP);
%plot(PP);
%plot(T,S(:,1:i));
grid;
title('KD-PLUS MULTI DIMENSIONAL SCALING');
xlabel('Time');
ylabel('Similarity');



%file = fopen('c:\MATLAB\work\mdsres.dat','w+');
file = fopen('c:\CUTS\data\mdsres.dat','w+');
%count = fprintf(file, '%f ',Y(:,1));
count = fprintf(file, '%f ',abs(sum(S(:,1:i)')'));
fclose(file);