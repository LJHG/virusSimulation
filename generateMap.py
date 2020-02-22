f = open('map.txt','w')
f.write('50\n')
f.write('50\n')
for i in range(50):
    if(i == 0):
        for j in range(50):
            f.write('1')
        f.write('\n')
    elif(i == 49):
        for j in range(50):
            f.write('1')
        f.write('\n')
    else:
        for j in range(50):
            f.write('0')
        f.write('\n')