print(""" Welcome to ARI calculator

        
         1. Press 1 for Addition
         2. Press 2 for Subtraction
         3. Press 3 for Multply
         4. Press 4 for Division

 """)
cal = int(input("Enter a Number for choose operand"))
cal1 = int(input("Enter first number: "))
cal2 = int(input("Enter second number: "))
if cal == 1:
  print(cal1+cal2)
elif cal == 2:
  print(cal1-cal2)
elif cal == 3:
  print(cal1*cal2)
elif cal == 4:
    print(cal1/cal2)
else:
    print("Enter a valid number")