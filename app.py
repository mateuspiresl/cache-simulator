import pygal
import csv

def create_chart(name, labels, unique_l2_data, unique_l1_data, double_data):
	bar_chart = pygal.Bar()
	bar_chart.x_labels = labels
	bar_chart.add('Unique L2', unique_l2_data)
	bar_chart.add('Unique L1', unique_l1_data)
	bar_chart.add('Double', double_data)
	bar_chart.render_to_file(name + '.svg')

with open('data.csv', newline='\n') as csvfile:
	spamreader = csv.reader(csvfile, delimiter=',')

	current_jump = False
	labels = []
	unique_l2_data = []
	unique_l1_data = []
	double_data = []

	for row in spamreader:
		jump = float(row[0])
		l2_size = int(row[1])
		l1_size = int(row[2])
		l2_l1_size = int(row[3])
		unique_l2_test = int(row[4])
		unique_l1_test = int(row[5])
		double_test = int(row[6])

		if current_jump != False and current_jump != jump:
			create_chart(str(current_jump), labels, unique_l2_data, unique_l1_data, double_data)

			labels = []
			unique_l2_data = []
			unique_l1_data = []
			double_data = []

		current_jump = jump
		labels.append(str(l2_size) + ' ' + str(l1_size) + ' ' + str(l2_l1_size))
		unique_l2_data.append(unique_l2_test)
		unique_l1_data.append(unique_l1_test)
		double_data.append(double_test)
		
	create_chart(str(current_jump), labels, unique_l2_data, unique_l1_data, double_data)