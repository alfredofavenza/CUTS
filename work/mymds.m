function mymds

load c:\CutsDEV\data\vettoriTFIDF.dat;
%load vettoriTFIDF.dat;
D = pdist(vettoriTFIDF,'euclidean');
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
plot(PP);
%plot(S(:,1:i));


%file = fopen('c:\MATLAB\work\mdsres.dat','w+');
file = fopen('c:\CutsDEV\data\mdsres.dat','w+');
%count = fprintf(file, '%f ',Y(:,1));
count = fprintf(file, '%f ',abs(sum(S(:,1:i)')'));
fclose(file);