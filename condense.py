import csv

def filter_us_titles(input_file, output_file):
    # Increase the CSV field size limit to a large value
    csv.field_size_limit(10**6)  # Set to 1,000,000 or another large value
    
    with open(input_file, mode='r', encoding='utf-8') as infile, open(output_file, mode='w', encoding='utf-8', newline='') as outfile:
        reader = csv.reader(infile, delimiter='\t')
        writer = csv.writer(outfile, delimiter='\t')
        
        # Write the header for the filtered file
        writer.writerow(['titleId', 'title'])
        
        # Filter and write relevant rows
        for row in reader:
            region = row[3]
            
            # Include only rows where region is 'US'
            if region == 'US':
                titleId = row[0]
                title = row[2]
                writer.writerow([titleId, title])

# Define the input and output file paths
input_file = 'title.akas.tsv'  # Replace with your actual input file path
output_file = 'filtered_title.akas.tsv'  # Replace with your desired output file path

# Filter the data
filter_us_titles(input_file, output_file)